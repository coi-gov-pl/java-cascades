package pl.gov.coi.cascades.contract.configuration;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.Template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 21.02.17.
 */
public class DriverTest {

    private Driver driver;

    @Mock
    private DatabaseType databaseType;

    @Mock
    private Template template;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        driver = new Driver(
            databaseType,
            template
        );
    }

    @Test
    public void testDefaultConstructor() throws Exception {
        // when
        Driver actual = new Driver(
            databaseType,
            template
        );

        // then
        assertNotNull(actual);
    }

    @Test
    public void testGetTemplateId() throws Exception {
        // when
        Optional<Template> actual = driver.getTemplateId();

        // then
        assertEquals(Optional.fromNullable(template), actual);
    }

}
