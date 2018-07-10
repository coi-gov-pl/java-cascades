package pl.gov.coi.cascades.server.domain.loadtemplate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.contract.domain.TemplateIdStatus;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseTemplateGateway;
import pl.gov.coi.cascades.server.domain.DatabaseUserGateway;
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
    private final TemplateIdGeneratorService templateIdGeneratorService;

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
                unzippedValidator.addValidatedEntityListener(metadataHolder::setTemplateMetadata);
                if (unzippedValidator.isValid()) {
                    Template template = createTemplate(metadataHolder);
                    loadTemplate(template, unzippedValidator.getPathToScript());
                    succeedResponse(template, response);
                }
            }
        }
    }

    private Template createTemplate(MetadataHolder metadataHolder) {
        String generatedId = templateIdGeneratorService.generateTemplateId();

        return Template.builder()
            .generatedId(generatedId)
            .version(metadataHolder.getTemplateMetadata().getVersion())
            .serverId(metadataHolder.getTemplateMetadata().getServerId())
            .isDefault(metadataHolder.getTemplateMetadata().isDefault())
            .status(TemplateIdStatus.CREATED)
            .name(metadataHolder.getTemplateMetadata().getName())
            .build();
    }

    private void loadTemplate(Template template, Path path) {
        databaseTemplateGateway.createTemplate(template, path);
    }

    private void succeedResponse(Template template, Response response) {
        templateIdGateway.addTemplate(template);

        response.setId(template.getGeneratedId());
        response.setName(template.getName());
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
    }

}
