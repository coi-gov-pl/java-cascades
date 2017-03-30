package pl.gov.coi.cascades.server.domain.launchdatabase;

import org.junit.Test;
import pl.gov.coi.cascades.contract.domain.DatabaseId;

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
        DatabaseId actual1 = databaseNameGeneratorService.generate("oracle");
        DatabaseId actual2 = databaseNameGeneratorService.generate("db");

        // then
        assertThat(actual1).isNotNull();
        assertThat(actual1.getId()).isNotNull();
        assertThat(actual1.getId().length()).isEqualTo(8);
        assertThat(actual1.getId()).startsWith("ora");
        assertThat(actual2).isNotNull();
        assertThat(actual2.getId()).isNotNull();
        assertThat(actual2.getId().length()).isEqualTo(8);
        assertThat(actual2.getId()).startsWith("db");
    }

    @Test
    public void testGenerate() throws Exception {
        // given
        DatabaseNameGeneratorService databaseNameGeneratorService = new DatabaseNameGeneratorService();

        // when
        DatabaseId actual = databaseNameGeneratorService.generate();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getId().length()).isEqualTo(8);
    }

}
