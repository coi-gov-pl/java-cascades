package pl.gov.coi.cascades.contract.configuration;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.NetworkBind;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 21.02.17.
 */
public class ConfigurationTest {

    private Configuration configuration;
    private String instanceName;

    @Mock
    private Server server;

    @Mock
    private Migration migration;

    @Mock
    private Driver driver;

    @Mock
    private NetworkBind networkBind;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        instanceName = "PESEL";
        configuration = new Configuration(
                server,
                migration,
                driver,
                instanceName,
                networkBind
        );
    }

    @Test
    public void testDefaultConstructor() throws Exception {
        // when
        Configuration actual = new Configuration(
                server,
                migration,
                driver,
                false,
                instanceName,
                networkBind
        );

        // then
        assertNotNull(actual);
    }

    @Test
    public void testGetInstanceName() throws Exception {
        // when
        Optional<String> actual = configuration.getInstanceName();

        // then
        assertEquals(Optional.fromNullable(instanceName), actual);
    }

    @Test
    public void testGetCascadesServerNetworkBind() throws Exception {
        // when
        NetworkBind actual = configuration.getNetworkBind();

        // then
        assertEquals(networkBind, actual);
    }

}
