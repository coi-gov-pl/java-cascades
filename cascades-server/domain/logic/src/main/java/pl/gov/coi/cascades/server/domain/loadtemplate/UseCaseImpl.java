package pl.gov.coi.cascades.server.domain.loadtemplate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;

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

    private static void succeedResponse(Response response,
                                        Validator validator) {
        response.setId(validator.getId());
        response.setDefault(validator.isDefault());
        response.setServerId(validator.getServerId());
        response.setStatus(validator.getStatus());
        response.setVersion(validator.getVersion());
    }

}
