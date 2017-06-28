package pl.gov.coi.cascades.server.domain.loadtemplate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.contract.domain.TemplateIdStatus;
import pl.gov.coi.cascades.server.domain.DatabaseTemplateGateway;
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
    private final DatabaseTemplateGateway databaseTemplateGateway;

    @Override
    public void execute(Request request, Response response) {
        try (ZipArchive zipArchive = new ZipArchive(request.getUpload())) {
            UploadValidator uploadValidator = new UploadValidator(zipArchive);
            uploadValidator.addViolationListener(response::addViolation);
            if (uploadValidator.isValid()) {

                Path path = zipArchive.ensureUnzipped().getPath();
                UnzippedValidator unzippedValidator = new UnzippedValidator(zipArchive, path);
                unzippedValidator.addViolationListener(response::addViolation);
                MetadataHolder metadataHolder = new MetadataHolder();
                unzippedValidator.addValidatedEntityListener(metadataHolder::setMetadata);
                if (unzippedValidator.isValid()) {
                    TemplateId templateId = createTemplate(metadataHolder);
                    loadTemplate(templateId, path);
                    succeedResponse(templateId, response);
                }
            }
        }
    }

    private static TemplateId createTemplate(MetadataHolder metadataHolder) {
        return TemplateId.builder()
            .version(metadataHolder.getTemplateMetadata().getVersion())
            .serverId(metadataHolder.getTemplateMetadata().getServerId())
            .isDefault(metadataHolder.getTemplateMetadata().isDefault())
            .status(TemplateIdStatus.CREATED)
            .id(metadataHolder.getTemplateMetadata().getId())
            .build();
    }

    private void loadTemplate(TemplateId templateId, Path path) {
        databaseTemplateGateway.createTemplate(templateId, path);
    }

    private void succeedResponse(TemplateId templateId, Response response) {
        templateIdGateway.addTemplate(templateId);

        response.setId(templateId.getId());
        response.setDefault(templateId.isDefault());
        response.setServerId(templateId.getServerId());
        response.setVersion(templateId.getVersion());
        response.setStatus(templateId.getStatus().name());
    }

    private static final class MetadataHolder {
        @Getter
        @Setter
        private TemplateMetadata templateMetadata;

        private void setMetadata(TemplateMetadata templateMetadata) {
            this.templateMetadata = templateMetadata;
        }
    }

}
