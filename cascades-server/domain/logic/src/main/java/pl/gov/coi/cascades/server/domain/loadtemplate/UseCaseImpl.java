package pl.gov.coi.cascades.server.domain.loadtemplate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.contract.domain.TemplateId.TemplateIdBuilder;
import pl.gov.coi.cascades.contract.domain.TemplateIdStatus;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;

import java.nio.file.Path;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.05.17.
 */
@Builder
@AllArgsConstructor
public class UseCaseImpl implements UseCase {

    private final TemplateIdGateway templateIdGateway;

    @Override
    public void execute(Request request, Response response) {
        try (ZipArchive zipArchive = new ZipArchive(request.getUpload())) {
            UploadValidator uploadValidator = new UploadValidator(zipArchive);
            uploadValidator.addViolationListener(response::addViolation);
            if (uploadValidator.isValid()) {

                Path path = zipArchive.ensureUnzipped().getPath();
                UnzippedValidator unzippedValidator = new UnzippedValidator(zipArchive, path);
                unzippedValidator.addViolationListener(response::addViolation);
                if (unzippedValidator.isValid()) {
                    loadTemplate(path);
                    succeedResponse(response, unzippedValidator);
                }
            }
        }
    }

    private void loadTemplate(Path path) {

    }

    private void succeedResponse(Response response,
                                 UnzippedValidator validator) {
        TemplateIdBuilder candidateBuilder = TemplateId.builder()
            .id(validator.getId())
            .isDefault(validator.isDefault())
            .serverId(validator.getServerId())
            .version(validator.getVersion());

        if (validator.getStatus().equalsIgnoreCase(TemplateIdStatus.CREATED.name())) {
            candidateBuilder.status(TemplateIdStatus.CREATED);
        } else if (validator.getStatus().equals(TemplateIdStatus.DELETED.name())) {
            candidateBuilder.status(TemplateIdStatus.DELETED);
        }

        TemplateId candidateTemplateId = candidateBuilder.build();

        templateIdGateway.save(candidateTemplateId);

        response.setId(validator.getId());
        response.setDefault(validator.isDefault());
        response.setServerId(validator.getServerId());
        response.setStatus(validator.getStatus());
        response.setVersion(validator.getVersion());
    }

}
