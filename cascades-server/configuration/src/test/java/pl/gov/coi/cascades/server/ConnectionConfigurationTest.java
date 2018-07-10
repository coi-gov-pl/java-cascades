package pl.gov.coi.cascades.server;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 04.08.17
 */
public class ConnectionConfigurationTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testGetDriver() throws Exception {
        // given
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@//%s:%d/%s";
        ConnectionConfiguration connectionConfiguration = new ConnectionConfiguration(
            driver,
            url
        );

        // when
        String actual = connectionConfiguration.getDriverClass();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(driver);
    }

    @Test
    public void testGetUrl() throws Exception {
        // given
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@//%s:%d/%s";
        ConnectionConfiguration connectionConfiguration = new ConnectionConfiguration(
            driver,
            url
        );

        // when
        String actual = connectionConfiguration.getJdbcUrlTemplate();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(url);
    }


}
