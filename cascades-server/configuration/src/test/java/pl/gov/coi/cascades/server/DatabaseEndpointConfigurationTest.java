package pl.gov.coi.cascades.server;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import pl.gov.coi.cascades.server.domain.DatabaseTemplateGateway;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 11.07.17
 */
public class DatabaseEndpointConfigurationTest {

    @Mock
    private DatabaseManager databaseManager;

    @Mock
    private DriverManagerDataSource driver;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

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

        // when
        DatabaseManager actual = conf.produceDatabaseManager(
            driver
        );

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isInstanceOf(DatabaseManager.class);
    }

}
