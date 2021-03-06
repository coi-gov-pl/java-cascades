package pl.gov.coi.cascades.server.presentation.launchdatabase;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.04.17.
 */
public class InputTemplateTest {

    @Test
    public void testParameterConstructor() {
        // when
        String id = "oracle_template";
        InputTemplate actual = new InputTemplate(id);

        // then
        assertThat(actual).isNotNull();
    }

}
