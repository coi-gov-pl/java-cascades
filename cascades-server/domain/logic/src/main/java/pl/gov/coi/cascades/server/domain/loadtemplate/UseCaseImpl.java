package pl.gov.coi.cascades.server.domain.loadtemplate;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.05.17.
 */
@Builder
@AllArgsConstructor
public class UseCaseImpl implements UseCase {

    @Override
    public void execute(Request request, Response response) {
        Validator.ValidatorBuilder validatorBuilder = Validator.builder()
            .request(request)
            .response(response);

        Validator validator = validatorBuilder.build();
        if (validator.validate()) {
            succeedResponse(response, validator);
        }
    }

    private void succeedResponse(Response response,
                                 Validator validator) {

        response.setId(validator.getId());
        response.setDefault(validator.isDefault());
        response.setServerId(validator.getServerId());
        response.setStatus(validator.getStatus());
        response.setVersion(validator.getVersion());
    }

}
