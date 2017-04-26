package pl.gov.coi.cascades.server;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.04.17.
 */
public class EnvironmentTest {

    @Test
    public void testGetName() throws Exception {
        // when
        String actual = Environment.DEVELOPMENT.getName();

        // then
        assertThat(actual).isEqualTo("development");
    }

}
