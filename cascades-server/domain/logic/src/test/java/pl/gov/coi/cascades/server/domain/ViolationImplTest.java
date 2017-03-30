package pl.gov.coi.cascades.server.domain;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 22.03.17.
 */
public class ViolationImplTest {

    private ViolationImpl error;

    @Before
    public void setUp() {
        error = new ViolationImpl(
            "Error occurred.",
            "testPath"
        );
    }

    @Test
    public void testGetMessage() throws Exception {
        // when
        String actual = error.getMessage();

        // then
        assertThat(actual).isEqualTo("Error occurred.");
    }

    @Test
    public void testGetPropertyPath() throws Exception {
        // when
        String actual = error.getPropertyPath();

        // then
        assertThat(actual).isEqualTo("testPath");
    }

}
