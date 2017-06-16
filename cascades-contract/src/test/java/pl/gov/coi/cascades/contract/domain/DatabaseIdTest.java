package pl.gov.coi.cascades.contract.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 21.02.17.
 */
public class DatabaseIdTest {

    private String id;

    @Before
    public void setUp() {
        id = "1234567abcd";
    }

    @Test
    public void testToString() {
        // given
        DatabaseId databaseId = new DatabaseId(id);

        // when
        String actual = databaseId.toString();

        // then
        assertThat(actual).isEqualTo(databaseId.toString());
    }


    @Test
    public void testGetter() {
        // given
        DatabaseId databaseId = new DatabaseId(id);

        // when
        String actual = databaseId.getId();

        // then
        assertThat(actual).isEqualTo(id);
    }

    @Test
    public void testDefaultConstructor() throws Exception {
        // when
        DatabaseId actual = new DatabaseId(id);

        // then
        assertNotNull(actual);
    }

}
