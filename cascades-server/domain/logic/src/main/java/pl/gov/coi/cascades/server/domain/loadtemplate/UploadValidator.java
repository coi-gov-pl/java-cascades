package pl.gov.coi.cascades.server.domain.loadtemplate;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import pl.gov.coi.cascades.server.domain.AbstractListenableValidator;
import pl.gov.coi.cascades.server.domain.ViolationImpl;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.06.17.
 */
@RequiredArgsConstructor
class UploadValidator extends AbstractListenableValidator {

    private static final String APPLICATION_ZIP_CONTENT_TYPE = "application/zip";
    private static final String PROPERTY_PATH_ZIP_FORMAT = "template.format";
    private final ZipArchive zipArchive;
    private final Collection<Supplier<Boolean>> validators = validators(
        this::validateZip,
        this::validateEnoughSpacePresent
    );

    @Override
    public boolean isValid() {
        return validators
            .stream()
            .map(Supplier::get)
            .reduce(UploadValidator::and)
            .orElse(false);
    }

    @SafeVarargs
    private static Collection<Supplier<Boolean>> validators(Supplier<Boolean>... supplier) {
        return Lists.newArrayList(
            supplier
        );
    }

    private static boolean and(boolean first, boolean second) {
        return first && second;
    }

    private boolean validateEnoughSpacePresent() {
        long dirFreeSpace = zipArchive.getPath().toFile().getFreeSpace();
        long zipSize = zipArchive.getUpload().getSize();
        return zipSize <= dirFreeSpace;
    }

    private boolean validateZip() {
        if (!zipArchive.getUpload()
                .getContentType()
                .equals(APPLICATION_ZIP_CONTENT_TYPE)) {
            newError(
                PROPERTY_PATH_ZIP_FORMAT,
                "Loaded file is in the wrong format."
            );
            return false;
        }
        return  true;
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
