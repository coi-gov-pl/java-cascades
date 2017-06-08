package pl.gov.coi.cascades.server.domain.loadtemplate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.json.JSONObject;
import pl.gov.coi.cascades.server.domain.ViolationImpl;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static pl.wavesoftware.eid.utils.EidPreconditions.checkNotNull;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.05.17.
 */
@Builder
@AllArgsConstructor
class Validator {

    private static final String CONTENT_TYPE = "application/zip";
    private static final String PROPERTY_PATH_ZIP_FORMAT = "template.format";
    private static final String PROPERTY_PATH_JSON_CONTAINING = "template.json";
    private static final String PROPERTY_PATH_JSON_STRUCTURE = "template.json.structure";
    private static final String PROPERTY_PATH_NO_SQL_FORMAT = "template.format.sql";
    private static final String PROPERTY_PATH_LACK_OF_SQL = "template.scripts";
    public static final String DEPLOY_SCRIPT = "deployScript";
    public static final String UNDEPLOY_SCRIPT = "undeployScript";
    public static final String USER_HOME = "user.home";
    public static final int BUFFER_SIZE = 2048;
    public static final int FILE_BUFFER_SIZE = 1024;
    private final Response response;
    private final Request request;
    private String id;
    private boolean isDefault;
    private String serverId;
    private String status;
    private String version;
    private String jsonFilename;

    public boolean validate() {
        validateZip();
        validateIfZipContainsJsonFile();
        validateJsonFileStructure();
        validateScriptsFormat();
        validateIfScriptsExist();
        return response.isSuccessful();
    }

    String getId() {
        return checkNotNull(id, "20170524:122357");
    }

    boolean isDefault() {
        return checkNotNull(isDefault, "20170524:122601");
    }

    String getStatus() {
        return checkNotNull(status, "20170524:122707");
    }

    String getServerId() {
        return checkNotNull(serverId, "20170524:122732");
    }

    String getVersion() {
        return checkNotNull(version, "20170524:122752");
    }

    private void validateZip() {
        if (!request.getContentType().equals(CONTENT_TYPE)) {
            newError(
                PROPERTY_PATH_ZIP_FORMAT,
                "Loaded file is in the wrong format."
            );
        }
    }

    private void validateIfZipContainsJsonFile() {
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(request.getZipFile()));
        ZipEntry entry;
        boolean containsJson = false;

        try {
            while ((entry = zis.getNextEntry()) != null) {
                String userHome = System.getProperty(USER_HOME);
                File file = new File(userHome + File.separator + entry.getName());

                int size;
                byte[] buffer = new byte[BUFFER_SIZE];

                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos, buffer.length);

                while ((size = zis.read(buffer, 0, buffer.length)) != -1) {
                    containsJson = isJsonExtension(entry);
                    bos.write(buffer, 0, size);
                }

                bos.flush();
                bos.close();
            }
        } catch (IOException e) {
            throw new EidIllegalStateException("20170605:113002", e);
        }

        if (!containsJson) {
            newError(
                PROPERTY_PATH_JSON_CONTAINING,
                "Loaded zip does not contains required JSON file."
            );
        }
    }

    private boolean isJsonExtension(ZipEntry entry) {
        boolean isJson = false;
        if (entry.getName().endsWith(".json")) {
            isJson = true;
            jsonFilename = entry.getName();
        }
        return isJson;
    }

    private static String readFileAsString(String filePath) throws IOException {
        StringBuilder fileData = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[FILE_BUFFER_SIZE];
        int numRead = 0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }

    private void validateJsonFileStructure() {
        String userHome = System.getProperty(USER_HOME);
        boolean hasFields = false;

        try {
            String jsonString = readFileAsString(userHome + File.separator + jsonFilename);
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject.has("name") &&
                jsonObject.has("isDefault") &&
                jsonObject.has("serverId") &&
                hasScripts(jsonObject)) {
                hasFields = true;

                id = jsonObject.getString("name");
                isDefault = jsonObject.getBoolean("isDefault");
                serverId = jsonObject.getString("serverId");
                status = jsonObject.getString("status");
                version = jsonObject.getString("version");
            }
        } catch (IOException e) {
            throw new EidIllegalStateException("20170605:130934", e);
        }

        if (!hasFields) {
            newError(
                PROPERTY_PATH_JSON_STRUCTURE,
                "Loaded JSON file does not have required fields."
            );
        }
    }

    private static boolean hasScripts(JSONObject jsonObject) {
        return jsonObject.has(DEPLOY_SCRIPT) &&
            jsonObject.has(UNDEPLOY_SCRIPT) &&
            jsonObject.has("status") &&
            jsonObject.has("version");
    }

    private void validateScriptsFormat() {
        String userHome = System.getProperty(USER_HOME);

        try {
            String jsonString = readFileAsString(userHome + File.separator + jsonFilename);
            JSONObject jsonObject = new JSONObject(jsonString);
            String deployStript = jsonObject.getString(DEPLOY_SCRIPT);
            String undeployScript = jsonObject.getString(UNDEPLOY_SCRIPT);
            if (!(deployStript.endsWith(".sql") && undeployScript.endsWith(".sql"))) {
                newError(
                    PROPERTY_PATH_NO_SQL_FORMAT,
                    "Deploy and undeploy script must be in .sql format"
                );
            }

        } catch (IOException e) {
            throw new EidIllegalStateException("20170607:151540", e);
        }
    }

    private void validateIfScriptsExist() {
        String userHome = System.getProperty(USER_HOME);

        try {
            String jsonString = readFileAsString(userHome + File.separator + jsonFilename);
            JSONObject jsonObject = new JSONObject(jsonString);
            String deployStript = jsonObject.getString(DEPLOY_SCRIPT);
            String undeployScript = jsonObject.getString(UNDEPLOY_SCRIPT);
            Path pathToDeployScript = Paths.get(userHome + File.separator + deployStript);
            Path pathToUndeployScript = Paths.get(userHome + File.separator + undeployScript);

            if (!(pathToDeployScript.toFile().exists() &&
                pathToUndeployScript.toFile().exists())) {
                newError(
                    PROPERTY_PATH_LACK_OF_SQL,
                    "Missing sql file/files in zip."
                );
            }
        } catch (IOException e) {
            throw new EidIllegalStateException("20170607:170042", e);
        }
    }

    private void newError(String path, String message, Object... parameters) {
        response.addViolation(
            new ViolationImpl(
                String.format(message, parameters),
                path
            )
        );
    }

}
