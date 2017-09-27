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
public class ConnectionDatabaseTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testGetJdbcTemplate() throws Exception {
        // given
        ConnectionDatabase connectionDatabase = new ConnectionDatabase(
            jdbcTemplate,
            "pgsql"
        );

        // when
        JdbcTemplate actual = connectionDatabase.getJdbcTemplate();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(jdbcTemplate);
    }

    @Test
    public void testGetType() throws Exception {
        // given
        ConnectionDatabase connectionDatabase = new ConnectionDatabase(
            jdbcTemplate,
            "pgsql"
        );

        // when
        String actual = connectionDatabase.getType();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo("pgsql");
    }

}
