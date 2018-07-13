package pl.gov.coi.cascades.server;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseTypeImpl;
import pl.gov.coi.cascades.server.domain.DatabaseUserGateway;
import pl.gov.coi.cascades.server.domain.launchdatabase.UsernameAndPasswordCredentialsImpl;
import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;

import java.sql.SQLException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
public class GeneralUserGatewayTest {

    private static final String SERVER_ID = "serverId";
    private static final String ORACLE = "ora12c";
    private static final String EXAMPLE_USERNAME = "exampleUsername";
    private static final String EXAMPLE_PASS = "examplePassword";
    private static final String TEMPLATE_NAME = "templateName";
    private static final String PGSQL = "pgsql";
    private static final String DATABASE_NAME = "databaseName";
    private static final String STUB = "STUB";

    @Mock
    private DatabaseManager databaseManager;

    @Mock
    private ConnectionDatabase connectionDatabase;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private UsernameAndPasswordCredentials credentials2;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private DatabaseUserGateway databaseUserGateway;

    @Before
    public void init() throws SQLException {
        databaseUserGateway = new GeneralUserGateway(databaseManager);
        given(databaseManager.getConnectionToServer(SERVER_ID)).willReturn(connectionDatabase);
        given(connectionDatabase.getJdbcTemplate()).willReturn(jdbcTemplate);
    }

    @Test
    public void shouldCreateUserPostgres() {
        //given
        char[] examplePass = EXAMPLE_PASS.toCharArray();
        given(credentials2.getPassword()).willReturn(examplePass);
        given(credentials2.getUsername()).willReturn(EXAMPLE_USERNAME);

        Template template = createTemplate();
        DatabaseInstance databaseInstance = DatabaseInstance.builder()
            .template(template)
            .databaseType(new DatabaseTypeImpl(PGSQL))
            .credentials(credentials2)
            .build();

        //when
        databaseUserGateway.createUser(databaseInstance);

        //then
        verify(jdbcTemplate).execute("CREATE USER exampleUsername WITH ENCRYPTED PASSWORD '" + examplePass + "'");
    }

    @Test
    public void shouldCreateUserOracle() throws SQLException {
        //given
        char[] examplePass = EXAMPLE_PASS.toCharArray();
        given(credentials2.getPassword()).willReturn(examplePass);
        given(credentials2.getUsername()).willReturn(EXAMPLE_USERNAME);

        Template template = createTemplate();
        DatabaseInstance databaseInstance = DatabaseInstance.builder()
            .template(template)
            .databaseType(new DatabaseTypeImpl(ORACLE))
            .credentials(credentials2)
            .build();

        given(databaseManager.getConnectionToDatabase(SERVER_ID, databaseInstance.getDatabaseName())).willReturn(connectionDatabase);

        //when
        databaseUserGateway.createUser(databaseInstance);

        //then
        verify(jdbcTemplate).execute("CREATE USER exampleUsername IDENTIFIED BY \"" + examplePass + "\"");
    }

    @Test
    public void shouldCreateAddPermissionsPostgres() {
        //given
        UsernameAndPasswordCredentials credentials = createUsernameAndPasswordCredentials();
        Template template = createTemplate();

        DatabaseInstance databaseInstance = DatabaseInstance.builder()
            .template(template)
            .credentials(credentials)
            .databaseName(DATABASE_NAME)
            .databaseType(new DatabaseTypeImpl(PGSQL))
            .build();

        //when
        databaseUserGateway.createUser(databaseInstance);

        //then
        verify(jdbcTemplate).execute("GRANT ALL PRIVILEGES ON DATABASE databaseName TO exampleUsername");
    }

    @Test
    public void shouldCreateAddPermissionsOracle() throws SQLException {
        //given
        UsernameAndPasswordCredentials credentials = createUsernameAndPasswordCredentials();
        Template template = createTemplate();

        DatabaseInstance databaseInstance = DatabaseInstance.builder()
            .template(template)
            .credentials(credentials)
            .databaseName(DATABASE_NAME)
            .databaseType(new DatabaseTypeImpl(ORACLE))
            .build();

        given(databaseManager.getConnectionToDatabase(SERVER_ID, databaseInstance.getDatabaseName())).willReturn(connectionDatabase);

        //when
        databaseUserGateway.createUser(databaseInstance);

        //then
        verify(jdbcTemplate).execute("GRANT DBA TO exampleUsername");
    }

    @Test
    public void shouldDeleteUserPostgres() {
        //given
        UsernameAndPasswordCredentials credentials = createUsernameAndPasswordCredentials();
        Template template = createTemplate();

        DatabaseInstance databaseInstance = DatabaseInstance.builder()
            .template(template)
            .databaseName(DATABASE_NAME)
            .databaseType(new DatabaseTypeImpl(PGSQL))
            .credentials(credentials)
            .build();

        //when
        databaseUserGateway.deleteUser(databaseInstance);

        //then
        verify(jdbcTemplate).execute("REVOKE ALL PRIVILEGES ON ALL TABLES IN SCHEMA public FROM exampleUsername");
        verify(jdbcTemplate).execute("REVOKE ALL ON DATABASE databaseName FROM exampleUsername");
        verify(jdbcTemplate).execute("DROP USER exampleUsername");
    }

    @Test
    public void shouldDeleteUserOracle() throws SQLException {
        //given
        UsernameAndPasswordCredentials credentials = createUsernameAndPasswordCredentials();
        Template template = createTemplate();

        DatabaseInstance databaseInstance = DatabaseInstance.builder()
            .template(template)
            .databaseName(DATABASE_NAME)
            .databaseType(new DatabaseTypeImpl(ORACLE))
            .credentials(credentials)
            .build();

        given(databaseManager.getConnectionToDatabase(SERVER_ID, databaseInstance.getDatabaseName())).willReturn(connectionDatabase);

        //when
        databaseUserGateway.deleteUser(databaseInstance);

        //then
        verify(jdbcTemplate).execute("DROP USER exampleUsername");
    }

    @Test(expected = EidIllegalArgumentException.class)
    public void shouldDeleteUserOracleThrowIllegalArgumentException() throws SQLException {
        //given
        Template template = createTemplate();
        DatabaseInstance databaseInstance = DatabaseInstance.builder()
            .template(template)
            .databaseName(DATABASE_NAME)
            .databaseType(new DatabaseTypeImpl(ORACLE))
            .build();

        given(databaseManager.getConnectionToDatabase(SERVER_ID, databaseInstance.getDatabaseName())).willThrow(new SQLException());

        //when
        databaseUserGateway.deleteUser(databaseInstance);

        //then
        expectedException.expectMessage("20180711:095808");
    }

    @Test(expected = EidIllegalArgumentException.class)
    public void shouldDeleteUserPostgresThrowIllegalArgumentException() throws SQLException {
        //given
        Template template = createTemplate();
        DatabaseInstance databaseInstance = DatabaseInstance.builder()
            .template(template)
            .databaseType(new DatabaseTypeImpl(PGSQL))
            .build();

        given(databaseManager.getConnectionToServer(SERVER_ID)).willThrow(new SQLException());

        //when
        databaseUserGateway.deleteUser(databaseInstance);

        //then
        expectedException.expectMessage("20180704:102108");
    }

    @Test(expected = EidIllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenCreateUser() throws SQLException {
        //given
        DatabaseInstance databaseInstance = DatabaseInstance.builder()
            .databaseType(new DatabaseTypeImpl(STUB))
            .build();

        //when
        databaseUserGateway.createUser(databaseInstance);

        //then
        expectedException.expectMessage("20180711:110816");
    }

    private UsernameAndPasswordCredentials createUsernameAndPasswordCredentials() {
        return new UsernameAndPasswordCredentialsImpl(
            EXAMPLE_USERNAME,
            EXAMPLE_PASS.toCharArray()
        );
    }

    private Template createTemplate() {
        return Template.builder()
            .serverId(SERVER_ID)
            .name(TEMPLATE_NAME)
            .build();
    }
}
