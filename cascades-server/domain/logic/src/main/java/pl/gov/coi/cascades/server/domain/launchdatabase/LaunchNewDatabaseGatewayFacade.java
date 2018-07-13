package pl.gov.coi.cascades.server.domain.launchdatabase;

import lombok.AllArgsConstructor;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseInstanceGateway;
import pl.gov.coi.cascades.server.domain.DatabaseLimitGateway;
import pl.gov.coi.cascades.server.domain.DatabaseOperationsGateway;
import pl.gov.coi.cascades.server.domain.DatabaseUserGateway;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.UserGateway;

import java.util.Optional;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 05.04.17.
 */
@AllArgsConstructor
public class LaunchNewDatabaseGatewayFacade {

    private TemplateIdGateway templateIdGateway;
    private UserGateway userGateway;
    private DatabaseLimitGateway databaseLimitGateway;
    private DatabaseInstanceGateway databaseInstanceGateway;
    private DatabaseOperationsGateway databaseOperationsGateway;
    private DatabaseUserGateway databaseUserGateway;

    Optional<Template> findTemplateId(String templateId) {
        return templateIdGateway.find(templateId);
    }

    Optional<Template> getDefaultTemplateId() {
        return templateIdGateway.getDefaultTemplateId();
    }

    Optional<User> findUser(String userName) {
        return userGateway.find(userName);
    }

    void save(User user) {
        userGateway.save(user);
    }

    DatabaseInstance launchDatabase(DatabaseInstance databaseInstance) {
        DatabaseInstance databaseInstanceWithSettings = databaseOperationsGateway.createDatabase(databaseInstance);
        databaseUserGateway.createUser(databaseInstanceWithSettings);
        return databaseInstanceGateway.save(databaseInstanceWithSettings);
    }

    DatabaseLimitGateway getDatabaseLimitGateway() {
        return databaseLimitGateway;
    }

    TemplateIdGateway getTemplateIdGateway() {
        return templateIdGateway;
    }
}
