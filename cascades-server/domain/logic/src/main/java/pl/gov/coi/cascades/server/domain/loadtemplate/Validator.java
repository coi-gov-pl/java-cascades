package pl.gov.coi.cascades.server.domain.loadtemplate;

import com.google.common.annotations.VisibleForTesting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.json.JSONObject;
import pl.gov.coi.cascades.server.domain.ViolationImpl;
import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
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
    private boolean containsJson;

    public boolean validate(String path) {
        validateZip();
        validateIfZipContainsJsonFile(path);
        validateJsonFileStructure(path);
        validateScriptsFormat(path);
        validateIfScriptsExist(path);
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

    @VisibleForTesting
    protected void validateZip() {
        if (!request.getContentType().equals(CONTENT_TYPE)) {
            newError(
                PROPERTY_PATH_ZIP_FORMAT,
                "Loaded file is in the wrong format."
            );
        }
    }

    @VisibleForTesting
    protected void validateIfZipContainsJsonFile(String path) {
        try {
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(request.getZipFile()));
            isContainsJson(path, zis);
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

    private void isContainsJson(String path,
                                ZipInputStream zis) throws IOException {
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            File file = new File(path + entry.getName());
            byte[] buffer = new byte[BUFFER_SIZE];
            try (FileOutputStream fos = new FileOutputStream(file)) {
                BufferedOutputStream bos = new BufferedOutputStream(fos, buffer.length);
                isJson(zis, entry, buffer, bos);
                bos.flush();
                bos.close();
            }
        }
    }

    private void isJson(ZipInputStream zis,
                        ZipEntry entry,
                        byte[] buffer,
                        BufferedOutputStream bos) throws IOException {
        int size;
        while ((size = zis.read(buffer, 0, buffer.length)) != -1) {
            if (entry.getName().endsWith(".json")) {
                containsJson = true;
                jsonFilename = entry.getName();
            }
            bos.write(buffer, 0, size);
        }
    }

    @VisibleForTesting
    protected static String readFileAsString(String filePath) {
        StringBuilder fileData = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            char[] buf = new char[FILE_BUFFER_SIZE];
            int numRead = 0;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
            }
        } catch (FileNotFoundException e) {
            throw new EidIllegalStateException("20170614:114408", e);
        } catch (IOException e) {
            throw new EidIllegalArgumentException("20170614:114429", e);
        }
        return fileData.toString();
    }

    @VisibleForTesting
    protected void validateJsonFileStructure(String path) {
        boolean hasFields = false;

        String jsonString = readFileAsString(path + jsonFilename);
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

    @VisibleForTesting
    protected void validateScriptsFormat(String path) {
        String jsonString = readFileAsString(path + jsonFilename);
        JSONObject jsonObject = new JSONObject(jsonString);
        String deployStript = jsonObject.getString(DEPLOY_SCRIPT);
        String undeployScript = jsonObject.getString(UNDEPLOY_SCRIPT);
        if (!(deployStript.endsWith(".sql") && undeployScript.endsWith(".sql"))) {
            newError(
                PROPERTY_PATH_NO_SQL_FORMAT,
                "Deploy and undeploy script must be in .sql format"
            );
        }
    }

    @VisibleForTesting
    protected void validateIfScriptsExist(String path) {
        String jsonString = readFileAsString(path + jsonFilename);
        JSONObject jsonObject = new JSONObject(jsonString);
        String deployStript = jsonObject.getString(DEPLOY_SCRIPT);
        String undeployScript = jsonObject.getString(UNDEPLOY_SCRIPT);
        Path pathToDeployScript = Paths.get(path + deployStript);
        Path pathToUndeployScript = Paths.get(path + undeployScript);

        if (!(pathToDeployScript.toFile().exists() &&
            pathToUndeployScript.toFile().exists())) {
            newError(
                PROPERTY_PATH_LACK_OF_SQL,
                "Missing sql file/files in zip."
            );
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
