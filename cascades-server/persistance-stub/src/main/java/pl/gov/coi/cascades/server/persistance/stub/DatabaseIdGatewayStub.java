package pl.gov.coi.cascades.server.persistance.stub;

import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;
import pl.gov.coi.cascades.server.domain.DatabaseIdGateway;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static pl.wavesoftware.eid.utils.EidPreconditions.checkNotNull;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 10.03.17.
 */
public class DatabaseIdGatewayStub implements DatabaseIdGateway {

    private static final String POS_34_T_56 = "pos34t56";
    public static final DatabaseId DATABASE_ID1 = new DatabaseId("19");
    public static final DatabaseId DATABASE_ID2 = new DatabaseId("pos56e78");
    public static final DatabaseType DATABASE_TYPE = new DatabaseTypeStub();
    public static final UsernameAndPasswordCredentials USERNAME_AND_PASSWORD_CREDENTIALS1 =
        new UsernameAndPasswordCredentialsStub("Ben Affleck");
    public static final UsernameAndPasswordCredentials USERNAME_AND_PASSWORD_CREDENTIALS2 =
        new UsernameAndPasswordCredentialsStub("Will Smith");
    public static final NetworkBind NETWORK_BIND = new NetworkBindStub(5432, "db01.lab.internal");
    public static final DatabaseInstance INSTANCE1 = new DatabaseInstance(
        DATABASE_ID1,
        TemplateIdGatewayStub.TEMPLATE_ID1,
        DATABASE_TYPE,
        "oracle 12c",
        1,
        "ora12e34",
        USERNAME_AND_PASSWORD_CREDENTIALS1,
        NETWORK_BIND,
        DatabaseStatus.LAUNCHED,
        Date.from(Instant.now())
    );
    public static final DatabaseInstance INSTANCE2 = new DatabaseInstance(
        DATABASE_ID2,
        TemplateIdGatewayStub.TEMPLATE_ID2,
        DATABASE_TYPE,
        POS_34_T_56,
        3,
        POS_34_T_56,
        USERNAME_AND_PASSWORD_CREDENTIALS2,
        NETWORK_BIND,
        DatabaseStatus.LAUNCHED,
        Date.from(Instant.now())
    );
    private Collection<DatabaseInstance> instances;

    public DatabaseIdGatewayStub() {
        this.instances = new HashSet<>();
        instances.add(INSTANCE1);
        instances.add(INSTANCE2);
    }

    @Override
    public Optional<DatabaseInstance> findInstance(DatabaseId databaseId) {
        for (DatabaseInstance databaseInstance : instances) {
            checkNotNull(databaseInstance, "20170320:160233");
            checkNotNull(databaseInstance.getDatabaseId(), "20170320:160259");
            checkNotNull(databaseInstance.getDatabaseId().getId(), "20170320:160338");
            if (databaseInstance.getDatabaseId().getId().equals(databaseId.getId())) {
                return Optional.of(databaseInstance);
            }
        }
        return Optional.empty();
    }

    public void addDatabaseInstance(DatabaseInstance databaseInstance) {
        instances.add(databaseInstance);
    }

    public Collection<DatabaseInstance> getAllInstances() {
        return cloneList(instances);
    }

    private List<DatabaseInstance> cloneList(Collection<DatabaseInstance> instanceList) {
        List<DatabaseInstance> clonedList = new ArrayList<>(instanceList.size());
        for (DatabaseInstance instance : instanceList) {
            clonedList.add(new DatabaseInstance(instance));
        }
        return clonedList;
    }

    public void clearInstances() {
        instances.clear();
    }

    public void removeDatabaseInstance(DatabaseInstance databaseInstance) {
        instances.remove(databaseInstance);
    }

}
