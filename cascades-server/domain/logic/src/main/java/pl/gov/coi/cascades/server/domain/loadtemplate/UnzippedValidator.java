package pl.gov.coi.cascades.server.domain.loadtemplate;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import pl.gov.coi.cascades.server.domain.AbstractListenableValidator;
import pl.gov.coi.cascades.server.domain.ViolationImpl;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static pl.wavesoftware.eid.utils.EidPreconditions.checkNotNull;

/**
 * @author agnieszka
 * @since 27.06.17
 */
@RequiredArgsConstructor
public class UnzippedValidator extends AbstractListenableValidator<TemplateMetadata> {

    private static final String PROPERTY_PATH_JSON = "template.json";
    private static final String DEPLOY_SCRIPT = "deployScript";
    private static final String PROPERTY_PATH_DEPLOY_SCRIPT = "template.deployScript";
    private final ZipArchive zipArchive;
    private final Path path;
    private final Collection<Supplier<Boolean>> validators = validators(
        this::validateJsonFileStructure,
        this::validateScriptsFormat,
        this::validateIfScriptsExist
    );
    private String jsonFilename;
    private TemplateMetadata templateMetadata;
    @Getter
    private Path pathToScript;

    @Override
    public boolean isValid() {
        boolean status = validateIfZipContainsJsonFile(path.toFile())
            ? validators
                .stream()
                .map(Supplier::get)
                .reduce(UnzippedValidator::and)
                .orElse(false)
            : false;
        if (status) {
            entityIsValid(templateMetadata);
        }
        return status;
    }

    private static boolean and(boolean first, boolean second) {
        return first && second;
    }

    @SafeVarargs
    private static Collection<Supplier<Boolean>> validators(Supplier<Boolean>... supplier) {
        return Lists.newArrayList(
            supplier
        );
    }

    private static boolean hasFields(JSONObject jsonObject) {
        return jsonObject.has(DEPLOY_SCRIPT) &&
            jsonObject.has("version");
    }

    private static String readFileAsString(String filePath) {
        StringBuilder fileData = new StringBuilder();

        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.forEach(fileData::append);
        } catch (IOException e) {
            throw new EidIllegalStateException("20170628:125113", e);
        }
        return fileData.toString();
    }

    private boolean validateScriptsFormat() {
        String jsonString = readFileAsString(path.resolve(jsonFilename).toString());
        JSONObject jsonObject = new JSONObject(jsonString);
        if (jsonObject.has(DEPLOY_SCRIPT)) {
            String deployScript = jsonObject.getString(DEPLOY_SCRIPT);
            if (!deployScript.endsWith(".sql")) {
                newError(
                    PROPERTY_PATH_DEPLOY_SCRIPT,
                    "Deploy script must be in .sql format"
                );
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean validateIfScriptsExist() {
        String jsonString = readFileAsString(path.resolve(jsonFilename).toString());
        JSONObject jsonObject = new JSONObject(jsonString);
        if (jsonObject.has(DEPLOY_SCRIPT)) {
            int position = jsonObject.getString(DEPLOY_SCRIPT).indexOf('.');
            String deployScript = jsonObject.getString(DEPLOY_SCRIPT).substring(0, position);
            String SQLFormat = ".sql";
            Path pathToDeployScript = path.resolve(deployScript + SQLFormat);
            if (!pathToDeployScript.toFile().exists()) {
                newError(
                    PROPERTY_PATH_DEPLOY_SCRIPT,
                    "Missing sql file/files in zip."
                );
                return false;
            }
            pathToScript = pathToDeployScript;
            return true;
        }
        return false;
    }

    private boolean validateJsonFileStructure() {
        boolean hasFields = false;

        String jsonString = readFileAsString(path.resolve(jsonFilename).toString());
        JSONObject jsonObject = new JSONObject(jsonString);
        if (jsonObject.has("name") &&
            jsonObject.has("isDefault") &&
            jsonObject.has("serverId") &&
            hasFields(jsonObject)) {
            hasFields = true;

            templateMetadata = TemplateMetadata.builder()
                .name(jsonObject.getString("name"))
                .isDefault(jsonObject.getBoolean("isDefault"))
                .serverId(jsonObject.getString("serverId"))
                .version(jsonObject.getString("version"))
                .build();
        }

        if (!hasFields) {
            newError(
                PROPERTY_PATH_JSON,
                "Loaded JSON file does not have required fields."
            );
            return false;
        }
        return true;
    }

    private boolean validateIfZipContainsJsonFile(File directory) {
        File[] fList = directory.listFiles();
        checkNotNull(fList, "20170628:104151");
        for (File file : fList) {
            if (file.getName().endsWith(".json")) {
                jsonFilename = file.getName();
                return true;

            }
        }

        newError(
            PROPERTY_PATH_JSON,
            "Loaded zip does not contain required JSON file."
        );
        return false;
    }

    private void newError(String path, String message, Object... parameters) {
        addNewViolation(
            new ViolationImpl(
                String.format(message, parameters),
                path
            )
        );
    }

}
