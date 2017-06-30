package pl.gov.coi.cascades.server.domain.launchdatabase;

import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseInstanceGateway;
import pl.gov.coi.cascades.server.domain.DatabaseLimitGateway;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.UserGateway;

import javax.inject.Inject;
import java.util.Optional;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 05.04.17.
 */
public class LaunchNewDatabaseGatewayFacade {

    private TemplateIdGateway templateIdGateway;
    private UserGateway userGateway;
    private DatabaseLimitGateway databaseLimitGateway;
    private DatabaseInstanceGateway databaseInstanceGateway;

    /**
     * Default parameter constructor.
     *
     * @param templateIdGateway Given gateway of templateId.
     * @param userGateway Given gateway of user.
     * @param databaseLimitGateway Given gateway of database limit.
     * @param databaseInstanceGateway Given gateway of database instance.
     */
    @Inject
    public LaunchNewDatabaseGatewayFacade(TemplateIdGateway templateIdGateway,
                                          UserGateway userGateway,
                                          DatabaseLimitGateway databaseLimitGateway,
                                          DatabaseInstanceGateway databaseInstanceGateway) {
        this.templateIdGateway = templateIdGateway;
        this.userGateway = userGateway;
        this.databaseLimitGateway = databaseLimitGateway;
        this.databaseInstanceGateway = databaseInstanceGateway;
    }

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
        return databaseInstanceGateway.launchDatabase(databaseInstance);
    }

    DatabaseLimitGateway getDatabaseLimitGateway() {
        return databaseLimitGateway;
    }

    TemplateIdGateway getTemplateIdGateway() {
        return templateIdGateway;
    }

}
