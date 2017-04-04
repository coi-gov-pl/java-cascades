package pl.gov.coi.cascades.supplier.string;

import org.junit.Test;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 30.03.17
 */
public class InputStreamStringTest {
    @Test
    public void testGet() {
        // given
        InputStream is = InputStreamStringTest.class
            .getResourceAsStream("a-file-with-utf8.txt");
        InputStreamString inputStreamString = new InputStreamString(is);

        // when
        String text = inputStreamString.get();

        // then
        assertThat(text)
            .isNotNull()
            .isNotEmpty()
            .contains("Suszyński");
    }

}
