package pl.gov.coi.cascades.server.domain.launchdatabase;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 07.03.17.
 */
public class DatabaseNameGeneratorServiceTest {

    @Test
    public void testGenerateWithParameter() throws Exception {
        // given
        DatabaseNameGeneratorService databaseNameGeneratorService = new DatabaseNameGeneratorService();

        // when
        String actual1 = databaseNameGeneratorService.generate("oracle");
        String actual2 = databaseNameGeneratorService.generate("db");

        // then
        assertThat(actual1).isNotNull();
        assertThat(actual1.length()).isEqualTo(8);
        assertThat(actual1).startsWith("ora");
        assertThat(actual2).isNotNull();
        assertThat(actual2).isNotNull();
        assertThat(actual2.length()).isEqualTo(8);
        assertThat(actual2).startsWith("db");
    }

    @Test
    public void testGenerate() throws Exception {
        // given
        DatabaseNameGeneratorService databaseNameGeneratorService = new DatabaseNameGeneratorService();

        // when
        String actual = databaseNameGeneratorService.generate();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isNotNull();
        assertThat(actual.length()).isEqualTo(8);
    }

}
