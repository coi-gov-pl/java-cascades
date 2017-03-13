package pl.gov.coi.cascades.server.domain.deletedatabase;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import pl.gov.coi.cascades.server.domain.DatabaseIdGateway;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseInstanceGateway;
import pl.gov.coi.cascades.server.domain.DatabaseStatus;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.UserGateway;

import java.util.Optional;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 09.03.17.
 */
@Builder
@RequiredArgsConstructor
public class DatabaseInstanceUseCaseImpl implements DatabaseInstanceUseCase {

    private final UserGateway userGateway;
    private final DatabaseIdGateway databaseIdGateway;
    private final DatabaseInstanceGateway databaseInstanceGateway;

    /**
     * This method takes a pair of request and response objects. That ensures decoupling of presentation from domain.
     *
     * @param request Given request of deleting launched database instance.
     * @param response Given response of deleting launched database instance.
     */
    @Override
    public void execute(DatabaseInstanceRequest request, DatabaseInstanceResponse response) {
        Optional<User> user = request.getUser() != null
            ? userGateway.find(request.getUser().getUsername())
            : Optional.empty();
        Optional<DatabaseInstance> databaseInstance = databaseIdGateway.findInstance(request.getDatabaseId());

        DatabaseInstanceValidator.DatabaseInstanceValidatorBuilder validatorBuilder = DatabaseInstanceValidator.builder()
            .request(request)
            .response(response);

        databaseInstance.ifPresent(databaseInstance1 -> validatorBuilder.databaseId(databaseInstance1.getDatabaseId()));
        user.ifPresent(validatorBuilder::user);

        DatabaseInstanceValidator validator = validatorBuilder.build();

        if (validator.validate()) {
            succeedResponse(validator, databaseInstance.get());
        }
    }

    private void succeedResponse(DatabaseInstanceValidator validator,
                                 DatabaseInstance databaseInstance) {
        DatabaseInstance actualDatabaseInstance = databaseInstance.setStatus(DatabaseStatus.DELETED);
        User user = validator.getUser();
        user = user.updateDatabaseInstance(actualDatabaseInstance);

        databaseInstanceGateway.deleteDatabase(actualDatabaseInstance);
        userGateway.save(user);
    }

}
