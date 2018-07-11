package pl.gov.coi.cascades.server;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import pl.gov.coi.cascades.server.domain.DatabaseOperationsGateway;
import pl.gov.coi.cascades.server.domain.DatabaseTemplateGateway;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 11.07.17
 */
public class DatabaseEndpointConfigurationTest {

    @Mock
    private DatabaseManager databaseManager;

    @Mock
    private DriverManagerDataSourceHelper driverManagerDataSourceHelper;

    @Mock
    private ConnectionConfigurator connectionConfigurator;

    @Mock
    private ServerConfigurationService serverConfigurationService;

    @Mock
    private DriverManagerDataSourceProvider driverManagerDataSourceProvider;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private DatabaseEndpointConfiguration databaseEndpointConfiguration = new DatabaseEndpointConfiguration();

    @Test
    public void testProduceConnectionConfiguration() {
        // when
        ConnectionConfigurator actual = databaseEndpointConfiguration.produceConnectionConfiguration();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testProduceDriverManagerDataSourceWhenMapIsEmpty() {
        // given
        Map<String, DriverManagerDataSource> expectedMap = new HashMap<>();
        when(driverManagerDataSourceHelper.getManagersMap()).thenReturn(expectedMap);

        // when
        Map<String, DriverManagerDataSource> actual = databaseEndpointConfiguration.produceDriverManagerDataSource(
            driverManagerDataSourceHelper
        );

        // then
        assertThat(actual).isEqualTo(expectedMap);
    }

    @Test
    public void testProduceDatabaseTemplateGateway() {
        // when
        DatabaseTemplateGateway actual = databaseEndpointConfiguration.produceDatabaseTemplateGateway(
            databaseManager
        );

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isInstanceOf(GeneralTemplateGateway.class);
    }

    @Test
    public void testProduceDatabaseManager() {
        // given
        Map<String, DriverManagerDataSource> drivers = new HashMap<>();

        // when
        DatabaseManager actual = databaseEndpointConfiguration.produceDatabaseManager(
            drivers,
            driverManagerDataSourceHelper
        );

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isInstanceOf(DatabaseEndpointManager.class);
    }

    @Test
    public void testProduceDriverManagerDataSourceHelper() {
        // when
        DriverManagerDataSourceHelper actual = databaseEndpointConfiguration.produceDriverManagerDataSourceHelper(
            connectionConfigurator,
            serverConfigurationService,
            driverManagerDataSourceProvider
        );

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testProduceDriverManagerDataSourceProvider() {
        // when
        DriverManagerDataSourceProvider actual = databaseEndpointConfiguration.produceDriverManagerDataSourceProvider();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void shouldProduceDatabaseOperationsGateway() {
        // when
        DatabaseOperationsGateway actual = databaseEndpointConfiguration.produceDatabaseOperationGateway(
            serverConfigurationService,
            databaseManager
        );

        // then
        assertThat(actual).isNotNull();
    }

}
