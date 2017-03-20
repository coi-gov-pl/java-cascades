package pl.gov.coi.cascades.server.persistance.stub;

import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;
import pl.gov.coi.cascades.server.domain.DatabaseIdGateway;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseStatus;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 10.03.17.
 */
public class DatabaseIdGatewayStub implements DatabaseIdGateway {

    public static final DatabaseId DATABASE_ID1 = new DatabaseId("ora12e34");
    public static final DatabaseId DATABASE_ID2 = new DatabaseId("pos56e78");
    public static final TemplateId TEMPLATE_ID1 = new TemplateId("oracle");
    public static final TemplateId TEMPLATE_ID2 = new TemplateId("postgres");
    public static final DatabaseType DATABASE_TYPE = new DatabaseTypeStub();
    public static final UsernameAndPasswordCredentials USERNAME_AND_PASSWORD_CREDENTIALS1 = new UsernameAndPasswordCredentialsStub("Ben Affleck");
    public static final UsernameAndPasswordCredentials USERNAME_AND_PASSWORD_CREDENTIALS2 = new UsernameAndPasswordCredentialsStub("Will Smith");
    public static final NetworkBind NETWORK_BIND = new NetworkBindStub(5432, "db01.lab.internal");
    public static final DatabaseInstance INSTANCE1 = new DatabaseInstance(
        DATABASE_ID1,
        TEMPLATE_ID1,
        DATABASE_TYPE,
        "ora12e34",
        1,
        "ora12e34",
        USERNAME_AND_PASSWORD_CREDENTIALS1,
        NETWORK_BIND,
        DatabaseStatus.LAUNCHED,
        Date.from(Instant.now())
    );
    public static final DatabaseInstance INSTANCE2 = new DatabaseInstance(
        DATABASE_ID2,
        TEMPLATE_ID2,
        DATABASE_TYPE,
        "pos56e78",
        3,
        "pos56e78",
        USERNAME_AND_PASSWORD_CREDENTIALS2,
        NETWORK_BIND,
        DatabaseStatus.LAUNCHED,
        Date.from(Instant.now())
    );
    private Collection<DatabaseInstance> instances;

    @Override
    public Optional<DatabaseInstance> findInstance(DatabaseId databaseId) {
        instances.stream()
            .filter(instance -> instance.getDatabaseId().equals(databaseId))
            .forEach(Optional::ofNullable);
        return Optional.empty();
    }

    public DatabaseIdGatewayStub() {
        this.instances = new HashSet<>();

        addDatabaseInstance(INSTANCE1);
        addDatabaseInstance(INSTANCE2);
    }

    public void addDatabaseInstance(DatabaseInstance databaseInstance) {
        instances.add(databaseInstance);
    }

    public Collection<DatabaseInstance> getAllInstances() {
        return instances;
    }

    public void clearInstances() {
        instances.clear();
    }

    public void removeDatabaseInstance(DatabaseInstance databaseInstance) {
        instances.remove(databaseInstance);
    }

}
