package pl.gov.coi.cascades.server.persistance.stub;

import org.junit.Test;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseStatus;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.04.17.
 */
public class DatabaseIdGatewayStubTest {

    @Test
    public void testFindInstanceWhenDatabaseIdIsPresent() throws Exception {
        // given
        DatabaseIdGatewayStub databaseIdGatewayStub = new DatabaseIdGatewayStub();

        // when
        Optional<DatabaseInstance> actual = databaseIdGatewayStub.findInstance(
            DatabaseIdGatewayStub.DATABASE_ID1
        );

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(DatabaseIdGatewayStub.INSTANCE1);
    }

    @Test
    public void testFindInstanceWhenDatabaseIdIsNotPresent() throws Exception {
        // given
        DatabaseIdGatewayStub databaseIdGatewayStub = new DatabaseIdGatewayStub();

        // when
        Optional<DatabaseInstance> actual = databaseIdGatewayStub.findInstance(
            new DatabaseId("nonExistingDatabaseId")
        );

        // then
        assertThat(actual).isEqualTo(Optional.empty());
    }

    @Test
    public void testAddDatabaseInstance() throws Exception {
        // given
        int NUMBER_OF_DATABASES = 3;
        DatabaseId DATABASE_ID1 = new DatabaseId("545745");
        DatabaseType DATABASE_TYPE = new DatabaseTypeStub();
        UsernameAndPasswordCredentials USERNAME_AND_PASSWORD_CREDENTIALS1 =
            new UsernameAndPasswordCredentialsStub("Kevin Costner");
        NetworkBind NETWORK_BIND = new NetworkBindStub(5432, "db01.lab.internal");
        DatabaseInstance INSTANCE1 = new DatabaseInstance(
            DATABASE_ID1,
            TemplateIdGatewayStub.TEMPLATE_ID1,
            DATABASE_TYPE,
            "postgres",
            1,
            "pos12e34",
            USERNAME_AND_PASSWORD_CREDENTIALS1,
            NETWORK_BIND,
            DatabaseStatus.LAUNCHED,
            Date.from(Instant.now())
        );
        DatabaseIdGatewayStub databaseIdGatewayStub = new DatabaseIdGatewayStub();

        // when
        databaseIdGatewayStub.addDatabaseInstance(INSTANCE1);
        int actual = databaseIdGatewayStub.getAllInstances().size();

        // then
        assertThat(actual).isEqualTo(NUMBER_OF_DATABASES);
    }

    @Test
    public void testGetAllInstances() throws Exception {
        // given
        int NUMBER_OF_DATABASES = 2;
        DatabaseIdGatewayStub databaseIdGatewayStub = new DatabaseIdGatewayStub();

        // when
        Collection<DatabaseInstance> actual = databaseIdGatewayStub.getAllInstances();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(NUMBER_OF_DATABASES);
    }

    @Test
    public void testClearInstances() throws Exception {
        // given
        int NUMBER_OF_DATABASES = 0;
        DatabaseIdGatewayStub databaseIdGatewayStub = new DatabaseIdGatewayStub();

        // when
        databaseIdGatewayStub.clearInstances();
        int actual = databaseIdGatewayStub.getAllInstances().size();

        // then
        assertThat(actual).isEqualTo(NUMBER_OF_DATABASES);
    }

    @Test
    public void testRemoveDatabaseInstance() throws Exception {
        // given
        int NUMBER_OF_DATABASES = 1;
        DatabaseIdGatewayStub databaseIdGatewayStub = new DatabaseIdGatewayStub();

        // when
        databaseIdGatewayStub.removeDatabaseInstance(
            DatabaseIdGatewayStub.INSTANCE1
        );
        int actual = databaseIdGatewayStub.getAllInstances().size();

        // then
        assertThat(actual).isEqualTo(NUMBER_OF_DATABASES);
    }

}
