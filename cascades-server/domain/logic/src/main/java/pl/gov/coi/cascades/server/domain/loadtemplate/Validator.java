package pl.gov.coi.cascades.server.domain.loadtemplate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.json.JSONObject;
import pl.gov.coi.cascades.contract.domain.TemplateIdStatus;
import pl.gov.coi.cascades.server.domain.ViolationImpl;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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
    private final Response response;
    private final Request request;
    private String id;
    private boolean isDefault;
    private String serverId;
    private TemplateIdStatus status;
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

    TemplateIdStatus getStatus() {
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
                String userHome = System.getProperty("user.home");
                File file = new File(userHome + File.separator + entry.getName());

                int size;
                byte[] buffer = new byte[2048];

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

    private String readFileAsString(String filePath) throws IOException {
        StringBuilder fileData = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }

    private void validateJsonFileStructure() {
        String userHome = System.getProperty("user.home");
        boolean hasFields = false;

        try {
            String jsonString = readFileAsString(userHome + File.separator + jsonFilename);
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject.has("name") &&
                jsonObject.has("isDefault") &&
                jsonObject.has("version") &&
                hasScripts(jsonObject)) {
                hasFields = true;
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

    private boolean hasScripts(JSONObject jsonObject) {
        return jsonObject.has("deployScript") &&
            jsonObject.has("undeployScript");
    }

    private void validateScriptsFormat() {
        String userHome = System.getProperty("user.home");

        try {
            String jsonString = readFileAsString(userHome + File.separator + jsonFilename);
            JSONObject jsonObject = new JSONObject(jsonString);
            String deployStript = jsonObject.getString("deployScript");
            String undeployScript = jsonObject.getString("undeployScript");
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
