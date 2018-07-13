package pl.gov.coi.cascades.server;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseOperationsGateway;
import pl.gov.coi.cascades.server.domain.DatabaseStatus;
import pl.gov.coi.cascades.server.domain.DatabaseTypeImpl;
import pl.gov.coi.cascades.server.domain.launchdatabase.UsernameAndPasswordCredentialsImpl;
import pl.gov.coi.cascades.server.persistance.stub.DatabaseTypeStub;
import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
public class GeneralDatabaseOperationGatewayTest {

    private static final String TEMPLATE_GENERATED_ID = "4563462";
    private static final long TEMPLATE_ID = 123L;
    private static final String SERVER_ID = "serverId";
    private static final String EXAMPLE_HOST = "example.host";
    private static final int PORT = 5342;
    private static final String ID = "a123xqw2";
    private static final String PGSQL = "pgsql";
    private static final String ORACLE = "ora12c";
    private static final String TEMPLATE_NAME = "templateName";
    private static final String DATABASE_NAME = "exampleDatabaseName";
    private static final String INSTANCE_NAME = "my_database";
    private DatabaseInstance databaseInstance;
    private DatabaseOperationsGateway databaseOperations;
    private Template template;
    private ServerDef serverDef;

    @Mock
    private ServerConfigurationService serverConfigurationService;

    @Mock
    private ConnectionDatabase connectionDatabase;

    @Mock
    private DatabaseManager databaseManager;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void init() {
        databaseOperations = new GeneralDatabaseOperationGateway(
            serverConfigurationService,
            databaseManager
        );

        template = Template.builder().id(TEMPLATE_ID).name(TEMPLATE_NAME).generatedId(TEMPLATE_GENERATED_ID).serverId(SERVER_ID).build();
        databaseInstance = createDatabaseInstance(template);
        given(connectionDatabase.getJdbcTemplate()).willReturn(jdbcTemplate);

        serverDef = new ServerDef();
        serverDef.setHost(EXAMPLE_HOST);
        serverDef.setPort(PORT);
        serverDef.setType(PGSQL);
        serverDef.setServerId(SERVER_ID);
    }

    @Test
    public void shouldCreateDatabaseFindNetworkBind() throws SQLException {
        //given
        List<ServerDef> serverDefList = new ArrayList<>();
        serverDef.setType(ORACLE);
        serverDefList.add(serverDef);

        given(serverConfigurationService.getManagedServers()).willReturn(serverDefList);
        given(databaseManager.getConnectionToServer(anyString())).willReturn(connectionDatabase);

        //when
        DatabaseInstance result = databaseOperations.createDatabase(databaseInstance);

        //then
        assertNotNull(result);
        assertNotNull(result.getNetworkBind());
        assertEquals("example.host", result.getNetworkBind().getHost());
        assertEquals(5342, result.getNetworkBind().getPort());
    }

    @Test
    public void shouldCreateDatabaseFindDatabaseType() throws SQLException {
        //given
        List<ServerDef> serverDefList = new ArrayList<>();
        serverDef.setType(ORACLE);
        serverDefList.add(serverDef);

        given(serverConfigurationService.getManagedServers()).willReturn(serverDefList);
        given(databaseManager.getConnectionToServer(anyString())).willReturn(connectionDatabase);


        //when
        DatabaseInstance result = databaseOperations.createDatabase(databaseInstance);

        //then
        assertNotNull(result);
        assertNotNull(result.getDatabaseType());
        assertEquals("ora12c", result.getDatabaseType().getName());
    }

    @Test(expected = EidIllegalArgumentException.class)
    public void shouldCreateDatabaseNoFindDatabaseType() {
        //given
        serverDef.setType(null);

        List<ServerDef> serverDefList = new ArrayList<>();
        serverDefList.add(serverDef);

        given(serverConfigurationService.getManagedServers()).willReturn(serverDefList);

        //when
        databaseOperations.createDatabase(databaseInstance);

        //then
        expectedException.expectMessage("20180706:151716");
        expectedException.expectMessage("Hasn't been found database type.");
    }

    @Test(expected = EidIllegalArgumentException.class)
    public void shouldCreateDatabaseNotFindTemplate() {
        //when
        databaseOperations.createDatabase(createDatabaseInstance(null));

        //then
        expectedException.expectMessage("20180706:151316");
        expectedException.expectMessage("Hasn't been found database type.");
    }

    @Test(expected = EidIllegalArgumentException.class)
    public void shouldCreateDatabaseNotFindHost() {
        //given
        serverDef.setHost(null);

        List<ServerDef> serverDefList = new ArrayList<>();
        serverDefList.add(serverDef);

        given(serverConfigurationService.getManagedServers()).willReturn(serverDefList);

        //when
        databaseOperations.createDatabase(databaseInstance);

        //then
        expectedException.expectMessage("20180628:191916");
        expectedException.expectMessage("Hasn't been found connection settings.");
    }

    @Test(expected = EidIllegalArgumentException.class)
    public void shouldCreateDatabaseNoFindPort() {
        //given
        serverDef.setPort(0);

        List<ServerDef> serverDefList = new ArrayList<>();
        serverDefList.add(serverDef);

        given(serverConfigurationService.getManagedServers()).willReturn(serverDefList);

        //when
        databaseOperations.createDatabase(databaseInstance);

        //then
        expectedException.expectMessage("20180628:191916");
        expectedException.expectMessage("Hasn't been found connection settings.");
    }

    @Test
    public void shouldExecuteDeleteOracleDatabase() throws SQLException {
        //given
        List<ServerDef> serverDefList = new ArrayList<>();
        serverDef.setType(ORACLE);
        serverDefList.add(serverDef);

        given(serverConfigurationService.getManagedServers()).willReturn(serverDefList);
        given(databaseManager.getConnectionToServer(anyString())).willReturn(connectionDatabase);

        //when
        DatabaseTypeImpl databaseType = new DatabaseTypeImpl(ORACLE);
        databaseOperations.deleteDatabase(databaseInstance.setDatabaseType(databaseType));

        //then
        verify(jdbcTemplate).execute("ALTER SESSION SET container = CDB$ROOT");
        verify(jdbcTemplate).execute("ALTER PLUGGABLE DATABASE exampleDatabaseName CLOSE IMMEDIATE");
        verify(jdbcTemplate).execute("DROP PLUGGABLE DATABASE exampleDatabaseName INCLUDING DATAFILES");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldExecuteDeletePostgresDatabase() throws SQLException {
        //given
        List<ServerDef> serverDefList = new ArrayList<>();
        serverDef.setType(PGSQL);
        serverDefList.add(serverDef);

        given(serverConfigurationService.getManagedServers()).willReturn(serverDefList);
        given(databaseManager.getConnectionToServer(anyString())).willReturn(connectionDatabase);

        //when
        DatabaseTypeImpl databaseType = new DatabaseTypeImpl(PGSQL);
        databaseOperations.deleteDatabase(databaseInstance.setDatabaseType(databaseType));

        verify(jdbcTemplate).execute("DROP PLUGGABLE DATABASE exampleDatabaseName INCLUDING DATAFILES");
    }

    @Test(expected = EidIllegalArgumentException.class)
    public void shouldDeleteDatabaseNotFindTemplate() {
        //when
        databaseOperations.deleteDatabase(databaseInstance);

        //then
        expectedException.expectMessage("20180711:120816");
        expectedException.expectMessage("Hasn't been found database type.");
    }

    @Test
    public void shouldCreateOracleDatabase() throws SQLException {
        //given
        List<ServerDef> serverDefList = new ArrayList<>();
        serverDef.setType(ORACLE);
        serverDefList.add(serverDef);

        given(serverConfigurationService.getManagedServers()).willReturn(serverDefList);
        given(databaseManager.getConnectionToServer(anyString())).willReturn(connectionDatabase);

        //when
        databaseOperations.createDatabase(databaseInstance);

        //then
        verify(jdbcTemplate).execute("ALTER SESSION SET container = CDB$ROOT");
        verify(jdbcTemplate).execute("CREATE PLUGGABLE DATABASE exampleDatabaseName from templateName " +
            "file_name_convert = ('/u01/app/oracle/oradata/orcl12c/templateName', '/u01/app/oracle/oradata/orcl12c/exampleDatabaseName')");
        verify(jdbcTemplate).execute("ALTER PLUGGABLE DATABASE exampleDatabaseName OPEN READ WRITE");
    }

    @Test
    public void shouldCreatePostgresDatabase() throws SQLException {
        //given
        List<ServerDef> serverDefList = new ArrayList<>();
        serverDef.setType(PGSQL);
        serverDefList.add(serverDef);

        given(serverConfigurationService.getManagedServers()).willReturn(serverDefList);
        given(databaseManager.getConnectionToServer(anyString())).willReturn(connectionDatabase);

        //when
        databaseOperations.createDatabase(databaseInstance);

        //then
        verify(jdbcTemplate).execute("CREATE DATABASE exampleDatabaseName TEMPLATE templateName");
    }


    private DatabaseInstance createDatabaseInstance(Template template) {
        return DatabaseInstance.builder()
            .databaseId(new DatabaseId(ID))
            .status(DatabaseStatus.LAUNCHED)
            .created(new Date())
            .credentials(new UsernameAndPasswordCredentialsImpl(null, null))
            .databaseName(DATABASE_NAME)
            .databaseType(new DatabaseTypeStub())
            .instanceName(INSTANCE_NAME)
            .networkBind(null)
            .reuseTimes(1)
            .template(template)
            .build();
    }
}
