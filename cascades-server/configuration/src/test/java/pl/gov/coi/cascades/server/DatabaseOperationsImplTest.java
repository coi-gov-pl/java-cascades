package pl.gov.coi.cascades.server;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseOperations;
import pl.gov.coi.cascades.server.domain.DatabaseStatus;
import pl.gov.coi.cascades.server.domain.launchdatabase.UsernameAndPasswordCredentialsImpl;
import pl.gov.coi.cascades.server.persistance.stub.DatabaseTypeStub;
import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
public class DatabaseOperationsImplTest {

    private static final String TEMPLATE_ID = "4563462";
    private static final String SERVER_ID = "serverId";
    private static final String EXAMPLE_HOST = "example.host";
    private static final int PORT = 5342;
    private static final String ID = "a123xqw2";
    private static final String PGSQL = "pqsql";
    private DatabaseInstance databaseInstance;
    private DatabaseOperations databaseOperations;
    private Template template;
    private ServerDef serverDef;

    @Mock
    private ServerConfigurationService serverConfigurationService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void init() {
        databaseOperations = new DatabaseOperationsImpl(
            serverConfigurationService
        );

        template = Template.builder().id(TEMPLATE_ID).serverId(SERVER_ID).build();
        databaseInstance = createDatabaseInstance(template);

        serverDef = new ServerDef();
        serverDef.setHost(EXAMPLE_HOST);
        serverDef.setPort(PORT);
        serverDef.setType(PGSQL);
        serverDef.setServerId(SERVER_ID);
    }

    @Test
    public void shouldCreateDatabaseFindNetworkBind() {
        //given
        List<ServerDef> serverDefList = new ArrayList<>();
        serverDefList.add(serverDef);

        when(serverConfigurationService.getManagedServers()).thenReturn(serverDefList);

        //when
        DatabaseInstance result = databaseOperations.createDatabase(databaseInstance);

        //then
        assertNotNull(result);
        assertNotNull(result.getNetworkBind());
        assertEquals("example.host", result.getNetworkBind().getHost());
        assertEquals(5342, result.getNetworkBind().getPort());
    }

    @Test
    public void shouldCreateDatabaseFindDatabaseType() {
        //given
        List<ServerDef> serverDefList = new ArrayList<>();
        serverDefList.add(serverDef);

        when(serverConfigurationService.getManagedServers()).thenReturn(serverDefList);

        //when
        DatabaseInstance result = databaseOperations.createDatabase(databaseInstance);

        //then
        assertNotNull(result);
        assertNotNull(result.getDatabaseType());
        assertEquals("pqsql", result.getDatabaseType().getName());
    }

    @Test(expected = EidIllegalArgumentException.class)
    public void shouldCreateDatabaseNoFindDatabaseType() {
        //given
        serverDef.setType(null);

        List<ServerDef> serverDefList = new ArrayList<>();
        serverDefList.add(serverDef);

        when(serverConfigurationService.getManagedServers()).thenReturn(serverDefList);

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
    public void shouldCreateDatabaseNoFindHost() {
        //given
        serverDef.setHost(null);

        List<ServerDef> serverDefList = new ArrayList<>();
        serverDefList.add(serverDef);

        when(serverConfigurationService.getManagedServers()).thenReturn(serverDefList);

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

        when(serverConfigurationService.getManagedServers()).thenReturn(serverDefList);

        //when
        databaseOperations.createDatabase(databaseInstance);

        //then
        expectedException.expectMessage("20180628:191916");
        expectedException.expectMessage("Hasn't been found connection settings.");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldExecuteDeleteDatabase() {
        //when
        databaseOperations.deleteDatabase(databaseInstance);
    }

    private DatabaseInstance createDatabaseInstance(Template template) {
        String databaseName = "oracle";
        String instanceName = "my_database";
        return DatabaseInstance.builder()
            .databaseId(new DatabaseId(ID))
            .status(DatabaseStatus.LAUNCHED)
            .created(new Date())
            .credentials(new UsernameAndPasswordCredentialsImpl(null, null))
            .databaseName(databaseName)
            .databaseType(new DatabaseTypeStub())
            .instanceName(instanceName)
            .networkBind(null)
            .reuseTimes(1)
            .template(template)
            .build();
    }
}
