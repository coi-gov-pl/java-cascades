package pl.gov.coi.cascades.server.domain.loadtemplate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.contract.domain.TemplateIdStatus;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;
import pl.gov.coi.cascades.contract.domain.TemplateId.TemplateIdBuilder;

import java.io.IOException;
import java.nio.file.Files;
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
        Path temporaryFolder;
        String prefix = "Cascades_Temp_Dir";
        try {
            temporaryFolder = Files.createTempDirectory(prefix);
            Validator.ValidatorBuilder validatorBuilder = Validator.builder()
                .request(request)
                .response(response);

            Validator validator = validatorBuilder.build();
            if (validator.validate(temporaryFolder.toString())) {
                succeedResponse(response, validator);
            }
        } catch (IOException e) {
            throw new EidIllegalStateException("20170626:134937", e);
        }
    }

    private void succeedResponse(Response response,
                                        Validator validator) {
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
