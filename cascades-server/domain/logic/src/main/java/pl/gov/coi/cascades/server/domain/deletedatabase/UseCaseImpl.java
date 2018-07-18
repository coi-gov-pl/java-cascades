package pl.gov.coi.cascades.server.domain.deletedatabase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseStatus;
import pl.gov.coi.cascades.server.domain.User;

import java.util.Optional;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 09.03.17.
 */
@Builder
@AllArgsConstructor
public class UseCaseImpl implements UseCase {

    private final DeleteDatabaseGatewayFacade databaseGatewayFacade;

    /**
     * This method takes a pair of request and response objects. That ensures decoupling of presentation from domain.
     *
     * @param request Given request of deleting launched database instance.
     * @param response Given response of deleting launched database instance.
     */
    @Override
    public void execute(Request request, Response response) {
        Optional<User> user = request.getUser() != null
            ? databaseGatewayFacade.findUser(request.getUser().getUsername())
            : Optional.empty();
        Optional<DatabaseInstance> databaseInstance = databaseGatewayFacade.findInstance(request.getDatabaseId());

        Validator.ValidatorBuilder validatorBuilder = Validator.builder()
            .request(request)
            .response(response);

        databaseInstance.ifPresent(databaseInstance1 -> validatorBuilder.databaseId(databaseInstance1.getDatabaseId()));
        user.ifPresent(validatorBuilder::user);

        Validator validator = validatorBuilder.build();

        if (validator.validate()) {
            databaseInstance.ifPresent(databaseInstance12 -> succeedResponse(validator, databaseInstance12));
        }
    }

    private void succeedResponse(Validator validator,
                                 DatabaseInstance databaseInstance) {
        DatabaseInstance actualDatabaseInstance = databaseInstance.setStatus(DatabaseStatus.DELETED);
        User user = validator.getUser();
        user = user.updateDatabaseInstance(actualDatabaseInstance);

        databaseGatewayFacade.deleteDatabase(actualDatabaseInstance);
        databaseGatewayFacade.save(user);
    }
}
