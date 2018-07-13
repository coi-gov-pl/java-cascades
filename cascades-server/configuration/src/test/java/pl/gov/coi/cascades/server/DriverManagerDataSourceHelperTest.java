package pl.gov.coi.cascades.server;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="mailto:mariusz.wyszomierski@coi.gov.pl">Mariusz Wyszomierski</a>
 */
public class DriverManagerDataSourceHelperTest {

    @InjectMocks
    private DriverManagerDataSourceHelper dataSourceHelper;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private ServerConfigurationService serverConfigurationService;

    @Mock
    private DriverManagerDataSourceProvider driverManagerDataSourceProvider;

    @Mock
    private DriverManagerDataSource driverManagerDataSource;

    @Mock
    private ConnectionConfigurator connectionConfigurator;

    private static ServerDef SERVER_DEF;
    private static ConnectionConfiguration CONNECTION_CONFIGURATION;

    @Before
    public void setup() {
        String serverId = "serverId";
        String type = "oracle";
        String dbname = "dev";
        String user = "user";
        String password = "password";
        String host = "host.db";
        int port = 123;
        SERVER_DEF = new ServerDef(
            serverId,
            type,
            dbname,
            user,
            password,
            host,
            port
        );
        String urlTemplate = "jdbc://%s:%s/%s";
        String driverClass = "db.driver.class";
        CONNECTION_CONFIGURATION = new ConnectionConfiguration(driverClass, urlTemplate);
        when(serverConfigurationService.getManagedServers()).thenReturn(Lists.newArrayList(
            SERVER_DEF
        ));
        when(connectionConfigurator.getConnectionConfiguration(type)).thenReturn(CONNECTION_CONFIGURATION);
        when(driverManagerDataSourceProvider.produce()).thenReturn(driverManagerDataSource);
    }

    @Test
    public void testGetManager() {
        //when
        DriverManagerDataSource result = dataSourceHelper.getManager("serverId", "databaseName");

        //then
        assertDriverManagerDataSource(result, "jdbc://host.db:123/databaseName");
    }

    @Test
    public void testGetManagerWhenServerIdDoesntExists() {
        //then
        expectedException.expect(EidIllegalStateException.class);
        expectedException.expectMessage("20180625:205951");
        expectedException.expectMessage("There is no configuration for serverId: NotExistingServerId");

        //when
        dataSourceHelper.getManager("NotExistingServerId", "databaseName");
    }

    @Test
    public void testGetManagersMap() {
        //when
        Map<String, DriverManagerDataSource> resultMap = dataSourceHelper.getManagersMap();

        //then
        assertThat(resultMap.size()).isEqualTo(1);
        DriverManagerDataSource dataSource = resultMap.get("serverId");
        assertDriverManagerDataSource(dataSource, "jdbc://host.db:123/dev");
    }

    private void assertDriverManagerDataSource(DriverManagerDataSource dataSource, String url) {
        assertThat(dataSource).isNotNull();
        assertThat(dataSource).isEqualTo(driverManagerDataSource);
        verify(driverManagerDataSource).setDriverClassName(CONNECTION_CONFIGURATION.getDriverClass());
        verify(driverManagerDataSource).setUrl(url);
        verify(driverManagerDataSource).setPassword(SERVER_DEF.getPassword());
        verify(driverManagerDataSource).setUsername(SERVER_DEF.getUser());
    }

    @Test
    public void testGetManagersMapReturnEmptyMap() {
        //given
        when(serverConfigurationService.getManagedServers()).thenReturn(Lists.emptyList());

        //when
        Map<String, DriverManagerDataSource> resultMap = dataSourceHelper.getManagersMap();

        //then
        assertThat(resultMap.size()).isEqualTo(0);
    }
}
