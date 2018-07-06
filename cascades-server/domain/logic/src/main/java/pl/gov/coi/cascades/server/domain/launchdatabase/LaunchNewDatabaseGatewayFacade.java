package pl.gov.coi.cascades.server.domain.launchdatabase;

import lombok.AllArgsConstructor;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseInstanceGateway;
import pl.gov.coi.cascades.server.domain.DatabaseLimitGateway;
import pl.gov.coi.cascades.server.domain.DatabaseOperations;
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
    private DatabaseOperations databaseOperations;

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
        DatabaseInstance databaseInstanceWithSettings = databaseOperations.createDatabase(databaseInstance);
        // utworzyć użytkownika w bazie i nadać mu uprawnienia do nowej instancji stworzonej powyżej
        // TODO: write implementation
        // zapis instancji databaseInstance
        return databaseInstanceGateway.save(databaseInstanceWithSettings);
    }

    DatabaseLimitGateway getDatabaseLimitGateway() {
        return databaseLimitGateway;
    }

    TemplateIdGateway getTemplateIdGateway() {
        return templateIdGateway;
    }
}
