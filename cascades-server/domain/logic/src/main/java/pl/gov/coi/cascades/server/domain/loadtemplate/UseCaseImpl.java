package pl.gov.coi.cascades.server.domain.loadtemplate;

import lombok.*;
import pl.gov.coi.cascades.contract.domain.Template;
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
                    Template template = createTemplate(metadataHolder);
                    loadTemplate(template, path);
                    succeedResponse(template, response);
                }
            }
        }
    }

    private static Template createTemplate(MetadataHolder metadataHolder) {
        return Template.builder()
            .version(metadataHolder.getTemplateMetadata().getVersion())
            .serverId(metadataHolder.getTemplateMetadata().getServerId())
            .isDefault(metadataHolder.getTemplateMetadata().isDefault())
            .status(TemplateIdStatus.CREATED)
            .id(metadataHolder.getTemplateMetadata().getId())
            .build();
    }

    private void loadTemplate(Template template, Path path) {
        databaseTemplateGateway.createTemplate(template, path);
    }

    private void succeedResponse(Template template, Response response) {
        templateIdGateway.addTemplate(template);

        response.setId(template.getId());
        response.setDefault(template.isDefault());
        response.setServerId(template.getServerId());
        response.setVersion(template.getVersion());
        response.setStatus(template.getStatus().name());
    }

    @NoArgsConstructor
    private static final class MetadataHolder {
        @Getter
        @Setter
        private TemplateMetadata templateMetadata;

        private void setMetadata(TemplateMetadata templateMetadata) {
            this.templateMetadata = templateMetadata;
        }
    }

}
