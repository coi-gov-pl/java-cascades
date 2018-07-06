package pl.gov.coi.cascades.server;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseOperations;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;
import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
public class DatabaseOperationsImplTest {

    private static final String TEMPLATE_ID = "4563462";
    private static final String SERVER_ID = "serverId";
    private static final String EXAMPLE_HOST = "example.host";
    private static final int PORT = 5342;
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
            serverConfigurationService,
            templateIdGateway
        );

        template = Template.builder().id(TEMPLATE_ID).serverId(SERVER_ID).build();
        databaseInstance = DatabaseInstance.builder().template(template).build();
    }

    @Test
    public void shouldCreateDatabaseFindNetworkBind() {
        //given
        Optional<Template> templateOptional = Optional.of(template);
        ServerDef serverDef = new ServerDef();
        serverDef.setHost(EXAMPLE_HOST);
        serverDef.setPort(PORT);
        serverDef.setServerId(SERVER_ID);

        List<ServerDef> serverDefList = new ArrayList<>();
        serverDefList.add(serverDef);

        when(templateIdGateway.find(TEMPLATE_ID)).thenReturn(templateOptional);
        when(serverConfigurationService.getManagedServers()).thenReturn(serverDefList);

        //when
        NetworkBind result = databaseOperations.createDatabase(databaseInstance);

        //then
        assertEquals("example.host", result.getHost());
        assertEquals(5342, result.getPort());
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
}
