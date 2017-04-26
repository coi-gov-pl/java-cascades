package pl.gov.coi.cascades.server.domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 10.03.17.
 */
public class UserTest {

    private User user;
    private DatabaseInstance databaseInstance;

    @Mock
    private DatabaseId databaseId;

    @Mock
    private TemplateId templateId;

    @Mock
    private DatabaseType databaseType;

    @Mock
    private UsernameAndPasswordCredentials credentials;

    @Mock
    private NetworkBind networkBind;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        String username = "Kevin Spacey";
        String id = "kspacey";
        String email = "kevin.spacey@example.com";
        user = new User(
            username,
            id,
            email
        );
        DatabaseId databaseId = new DatabaseId("ora12e34");
        String instanceName = "PESEL";
        String databaseName = "orae231r";
        Date created = Date.from(Instant.now());
        databaseInstance = new DatabaseInstance(
            databaseId,
            templateId,
            databaseType,
            instanceName,
            0,
            databaseName,
            credentials,
            networkBind,
            DatabaseStatus.LAUNCHED,
            created
        );
    }

    @Test
    public void testUpdateDatabaseInstanceWhenDatabaseIdIsDifferent() throws Exception {
        // given
        String username = "Kevin Costner";
        String id = "kcostner";
        String email = "kevin.costner@example.com";
        user = new User(
            username,
            id,
            email
        );
        DatabaseId databaseId = new DatabaseId("ora23e45");
        String instanceName = "PESEL";
        String databaseName = "orae34325v";
        Date created = Date.from(Instant.now());
        DatabaseInstance notUserDatabase = new DatabaseInstance(
            databaseId,
            templateId,
            databaseType,
            instanceName,
            1,
            databaseName,
            credentials,
            networkBind,
            DatabaseStatus.LAUNCHED,
            created
        );
        User newUser = user.addDatabaseInstance(databaseInstance);

        // when
        User actual = newUser.updateDatabaseInstance(notUserDatabase);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isNotSameAs(newUser);
    }

    @Test
    public void testGetDatabaseSize() {
        // given
        String username = "John Rambo";
        String id = "fvjuge8u5yi";
        String email = "jrambo@example.com";
        Collection<DatabaseInstance> databases = new ArrayList<>();
        databases.add(databaseInstance);
        User user = new User(
            username,
            id,
            email,
            databases
        );

        // when
        int actual = user.getDatabasesSize();

        // then
        assertThat(actual).isEqualTo(1);
    }

    @Test
    public void testParameterConstructor() {
        // when
        String username = "John Rambo";
        String id = "fvjuge8u5yi";
        String email = "jrambo@example.com";
        Collection<DatabaseInstance> databases = new ArrayList<>();
        databases.add(databaseInstance);
        User actual = new User(
            username,
            id,
            email,
            databases
        );

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getUsername()).isEqualTo(username);
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getDatabases()).isNotNull();
        assertThat(actual.getDatabases()).hasSize(1);
    }

    @Test
    public void testAddDatabaseInstance() throws Exception {
        // when
        User actual = user.addDatabaseInstance(databaseInstance);
        ArrayList<DatabaseInstance> instanceList = new ArrayList<>();
        instanceList.add(databaseInstance);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isNotSameAs(user);
        assertThat(actual.getDatabases()).hasSize(1);
        assertThat(actual.getDatabases().iterator().next()).isEqualToComparingFieldByField(databaseInstance);
        assertThat(user.getDatabases()).doesNotContain(databaseInstance);
        assertThat(actual.getDatabases()).isNotSameAs(user.getDatabases());
    }

    @Test
    public void testUpdateDatabaseInstance() throws Exception {
        // given
        User newUser = user.addDatabaseInstance(databaseInstance);
        DatabaseInstance instance = databaseInstance.setStatus(DatabaseStatus.DELETED);

        // when
        User actual = newUser.updateDatabaseInstance(instance);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isNotSameAs(newUser);
    }

    @Test
    public void testGetDatabases() throws Exception {
        // when
        Iterable<DatabaseInstance> actual = user.getDatabases();

        // then
        assertThat(actual).isNotNull();
    }

}
