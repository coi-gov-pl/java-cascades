package pl.gov.coi.cascades.server.domain.deletedatabase;

import lombok.AllArgsConstructor;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.server.domain.DatabaseIdGateway;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseInstanceGateway;
import pl.gov.coi.cascades.server.domain.DatabaseOperationsGateway;
import pl.gov.coi.cascades.server.domain.DatabaseUserGateway;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.UserGateway;

import java.util.Optional;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
@AllArgsConstructor
public class DeleteDatabaseGatewayFacade {

    private final UserGateway userGateway;
    private final DatabaseIdGateway databaseIdGateway;
    private final DatabaseInstanceGateway databaseInstanceGateway;
    private final DatabaseOperationsGateway databaseOperationsGateway;
    private final DatabaseUserGateway databaseUserGateway;

    Optional<DatabaseInstance> findInstance(DatabaseId databaseId) {
        return databaseIdGateway.findInstance(databaseId);
    }

    Optional<User> findUser(String userName) {
        return userGateway.find(userName);
    }

    void save(User user) {
        userGateway.save(user);
    }

    void deleteDatabase(DatabaseInstance databaseInstance) {
        deleteInDatabase(databaseInstance);
        deleteInInformationDatabase(databaseInstance);
    }

    private void deleteInInformationDatabase(DatabaseInstance actualDatabaseInstance) {
        databaseInstanceGateway.deleteDatabase(actualDatabaseInstance);
    }

    private void deleteInDatabase(DatabaseInstance actualDatabaseInstance) {
        databaseUserGateway.deleteUser(actualDatabaseInstance);
        databaseOperationsGateway.deleteDatabase(actualDatabaseInstance);
    }
}
