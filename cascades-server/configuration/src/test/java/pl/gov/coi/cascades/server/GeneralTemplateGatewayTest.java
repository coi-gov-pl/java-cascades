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
import pl.gov.coi.cascades.server.domain.DatabaseTemplateGateway;
import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="mailto:mariusz.wyszomierski@coi.gov.pl">Mariusz Wyszomierski</a>
 */
public class GeneralTemplateGatewayTest {

    private static final String POSTGRESQL = "postgresql";
    private static final String SERVER_ID = "serverId";
    private static final String ORACLE = "oracle";
    private static final String DEPLOY_SCRIPT_CONTENT = "insert into some_table values ('test')";


    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private DatabaseManager databaseManager;

    @Mock
    private ConnectionDatabase connectionDatabase;

    @Mock
    private JdbcTemplate jdbcTemplate;

    private DatabaseTemplateGateway databaseTemplateGateway;
    private Template template = Template.builder().serverId(SERVER_ID).name("templateName").build();
    private Path deployScript = Paths.get("src","test","resources", "deploy.sql");

    @Before
    public void setup() throws SQLException {
        databaseTemplateGateway = new GeneralTemplateGateway(databaseManager);
        when(databaseManager.getConnectionToServer(SERVER_ID)).thenReturn(connectionDatabase);
        when(connectionDatabase.getJdbcTemplate()).thenReturn(jdbcTemplate);
        when(databaseManager.getConnectionToTemplate(SERVER_ID, "templateName")).thenReturn(connectionDatabase);
    }

    @Test
    public void shouldRunCreateDatabaseCommandInPostgreSQL() {
        //given
        when(connectionDatabase.getType()).thenReturn(POSTGRESQL);

        //when
        databaseTemplateGateway.createTemplate(template, deployScript);

        //then
        verify(jdbcTemplate).execute("CREATE DATABASE templateName TEMPLATE template0");
    }

    @Test
    public void shouldRunDeployScriptInPostgreSQL() {
        //given
        when(connectionDatabase.getType()).thenReturn(POSTGRESQL);

        //when
        databaseTemplateGateway.createTemplate(template, deployScript);

        //then
        verify(jdbcTemplate).execute(DEPLOY_SCRIPT_CONTENT);
    }

    @Test
    public void shouldChangeTemplateAttributesInPostgreSQL() {
        //given
        when(connectionDatabase.getType()).thenReturn(POSTGRESQL);

        //when
        databaseTemplateGateway.createTemplate(template, deployScript);

        //then
        verify(jdbcTemplate).execute("UPDATE pg_database SET datistemplate = TRUE WHERE datname = 'templateName'");
        verify(jdbcTemplate).execute("UPDATE pg_database SET datallowconn = FALSE WHERE datname = 'templateName'");
    }

    @Test
    public void shouldRunCreateDatabaseCommandInOracle() {
        //given
        when(connectionDatabase.getType()).thenReturn(ORACLE);

        //when
        databaseTemplateGateway.createTemplate(template, deployScript);

        //then
        verify(jdbcTemplate).execute("ALTER SESSION SET container = CDB$ROOT");
        verify(jdbcTemplate).execute("CREATE PLUGGABLE DATABASE templateName ADMIN USER admin IDENTIFIED " +
            "BY ksdn#2Hd file_name_convert = ('/u01/app/oracle/oradata/orcl12c/pdbseed'," +
            " '/u01/app/oracle/oradata/orcl12c/templateName')");
        verify(jdbcTemplate).execute("ALTER PLUGGABLE DATABASE templateName OPEN READ WRITE");
    }

    @Test
    public void shouldRunDeployScriptInOracle() {
        //given
        when(connectionDatabase.getType()).thenReturn(ORACLE);

        //when
        databaseTemplateGateway.createTemplate(template, deployScript);

        //then
        verify(jdbcTemplate).execute(DEPLOY_SCRIPT_CONTENT);
    }

    @Test(expected = EidIllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionCreateTemplate() throws SQLException {
        //given
        when(databaseManager.getConnectionToServer(SERVER_ID)).thenThrow(new SQLException());

        //when
        databaseTemplateGateway.createTemplate(template, deployScript);

        //then
        expectedException.expectMessage("20170711:151221");
    }

    @Test
    public void deleteTemplatePostgreSQL() {
        //given
        when(connectionDatabase.getType()).thenReturn(POSTGRESQL);

        //when
        databaseTemplateGateway.deleteTemplate(template);

        //then
        verify(jdbcTemplate).execute("DROP DATABASE templateName");
    }

    @Test
    public void deleteTemplateOracle() {
        //given
        when(connectionDatabase.getType()).thenReturn(ORACLE);

        //when
        databaseTemplateGateway.deleteTemplate(template);

        //then
        verify(jdbcTemplate).execute("ALTER PLUGGABLE DATABASE templateName CLOSE IMMEDIATE");
        verify(jdbcTemplate).execute("DROP PLUGGABLE DATABASE templateName INCLUDING DATAFILES");
    }

    @Test(expected = EidIllegalArgumentException.class)
    public void deleteTemplateShouldThrowIllegalArgumentException() throws SQLException {
        //given
        when(databaseManager.getConnectionToServer(SERVER_ID)).thenThrow(new SQLException());

        //when
        databaseTemplateGateway.deleteTemplate(template);

        //then
        expectedException.expectMessage("20170726:135511");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void canBeRemoved() {
        databaseTemplateGateway.canBeRemoved(template);
    }

    @Test(expected = EidIllegalStateException.class)
    public void shouldThrowExceptionWhenDeployScriptDoesntExistsCreateTemplate() {
        //given
        when(connectionDatabase.getType()).thenReturn(POSTGRESQL);

        //when
        databaseTemplateGateway.createTemplate(template, Paths.get("incorrectPath"));
    }
}
