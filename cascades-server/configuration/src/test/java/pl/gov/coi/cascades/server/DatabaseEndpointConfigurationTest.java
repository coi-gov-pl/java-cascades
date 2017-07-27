package pl.gov.coi.cascades.server;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import pl.gov.coi.cascades.server.domain.DatabaseTemplateGateway;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 11.07.17
 */
public class DatabaseEndpointConfigurationTest {

    @Mock
    private DatabaseManager databaseManager;

    @Mock
    private ServerConfigurationService service;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testProduceDriverManagerDataSourceWhenMapIsEmpty() throws Exception {
        // given
        DatabaseEndpointConfiguration conf = new DatabaseEndpointConfiguration();

        // when
        Map<String, DriverManagerDataSource> actual = conf.produceDriverManagerDataSource(
            service
        );

        // then
        assertThat(actual).isEmpty();
    }

    @Test
    public void testProduceDatabaseTemplateGateway() throws Exception {
        // given
        DatabaseEndpointConfiguration conf = new DatabaseEndpointConfiguration();

        // when
        DatabaseTemplateGateway actual = conf.produceDatabaseTemplateGateway(
            databaseManager
        );

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isInstanceOf(DatabaseTemplateGateway.class);
    }

    @Test
    public void testProduceDatabaseManager() throws Exception {
        // given
        DatabaseEndpointConfiguration conf = new DatabaseEndpointConfiguration();
        Map<String, DriverManagerDataSource> drivers = new HashMap<>();

        // when
        DatabaseManager actual = conf.produceDatabaseManager(
            drivers
        );

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isInstanceOf(DatabaseManager.class);
    }

}
