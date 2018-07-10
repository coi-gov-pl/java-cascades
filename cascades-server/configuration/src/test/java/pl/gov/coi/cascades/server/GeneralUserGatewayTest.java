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
    private static final String POSTGRESQL = "postgresql";
    private static final String ORACLE = "oracle";
    private static final String EXAMPLE_USERNAME = "exampleUsername";
    private static final String EXAMPLE_PASS = "examplePassword";
    private static final String TEMPLATE_NAME = "templateName";

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
            .credentials(credentials2)
            .build();

        given(connectionDatabase.getType()).willReturn(POSTGRESQL);

        //when
        databaseUserGateway.createUserPostgres(databaseInstance);

        //then
        verify(jdbcTemplate).execute("CREATE USER exampleUsername WITH ENCRYPTED PASSWORD '" + examplePass + "'");
    }

    @Test
    public void shouldCreateAddPermissionsPostgres() {
        //given
        UsernameAndPasswordCredentials credentials = createUsernameAndPasswordCredentials();
        Template template = createTemplate();

        DatabaseInstance databaseInstance = DatabaseInstance.builder()
            .template(template)
            .credentials(credentials)
            .build();

        given(connectionDatabase.getType()).willReturn(POSTGRESQL);

        //when
        databaseUserGateway.createUserPostgres(databaseInstance);

        //then
        verify(jdbcTemplate).execute("GRANT ALL PRIVILEGES ON DATABASE templateName TO exampleUsername");
    }

    @Test
    public void shouldDeleteUserPostgres() {
        //given
        UsernameAndPasswordCredentials credentials = createUsernameAndPasswordCredentials();
        Template template = createTemplate();

        DatabaseInstance databaseInstance = DatabaseInstance.builder()
            .template(template)
            .credentials(credentials)
            .build();

        given(connectionDatabase.getType()).willReturn(POSTGRESQL);

        //when
        databaseUserGateway.deleteUser(databaseInstance);

        //then
        verify(jdbcTemplate).execute("REVOKE ALL PRIVILEGES ON ALL TABLES IN SCHEMA public FROM exampleUsername");
        verify(jdbcTemplate).execute("REVOKE ALL ON DATABASE templateName FROM exampleUsername");
        verify(jdbcTemplate).execute("DROP USER exampleUsername");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldDeleteUserOracle() {
        //given
        Template template = createTemplate();
        DatabaseInstance databaseInstance = DatabaseInstance.builder()
            .template(template)
            .build();

        given(connectionDatabase.getType()).willReturn(ORACLE);

        //when
        databaseUserGateway.deleteUser(databaseInstance);
    }

    @Test(expected = EidIllegalArgumentException.class)
    public void shouldDeleteUserThrowIllegalArgumentException() throws SQLException {
        //given
        Template template = createTemplate();
        DatabaseInstance databaseInstance = DatabaseInstance.builder()
            .template(template)
            .build();

        given(databaseManager.getConnectionToServer(SERVER_ID)).willThrow(new SQLException());

        //when
        databaseUserGateway.deleteUser(databaseInstance);

        //then
        expectedException.expectMessage("20170726:135511");
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
