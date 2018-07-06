package pl.gov.coi.cascades.server;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseOperations;
import pl.gov.coi.cascades.server.domain.DatabaseStatus;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;
import pl.gov.coi.cascades.server.domain.launchdatabase.UsernameAndPasswordCredentialsImpl;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.Credentials;
import pl.gov.coi.cascades.server.persistance.stub.DatabaseTypeStub;
import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    private DatabaseInstance databaseInstance;
    private DatabaseOperations databaseOperations;
    private Template template;

    @Mock
    private ServerConfigurationService serverConfigurationService;

    @Mock
    private TemplateIdGateway templateIdGateway;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void init() {
        databaseOperations = new DatabaseOperationsImpl(
            serverConfigurationService
        );

        template = Template.builder().id(TEMPLATE_ID).serverId(SERVER_ID).build();
        databaseInstance = createDatabaseInstance(template);
    }

    @Test
    public void shouldCreateDatabaseFindNetworkBind() {
        //given
        ServerDef serverDef = new ServerDef();
        serverDef.setHost(EXAMPLE_HOST);
        serverDef.setPort(PORT);
        serverDef.setServerId(SERVER_ID);

        List<ServerDef> serverDefList = new ArrayList<>();
        serverDefList.add(serverDef);
        Template template = Template.builder()
            .serverId(SERVER_ID)
            .build();

        when(serverConfigurationService.getManagedServers()).thenReturn(serverDefList);

        //when
        DatabaseInstance result = databaseOperations.createDatabase(databaseInstance);

        //then
        assertNotNull(result);
        assertNotNull(result.getNetworkBind());
        assertEquals("example.host", result.getNetworkBind().getHost());
        assertEquals(5342, result.getNetworkBind().getPort());
    }

    @Test(expected = EidIllegalArgumentException.class)
    public void shouldCreateDatabaseNotFindTemplate() {
        //given
        when(templateIdGateway.find(TEMPLATE_ID)).thenReturn(null);

        //when
        databaseOperations.createDatabase(databaseInstance);
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
