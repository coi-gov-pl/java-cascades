package pl.gov.coi.cascades.contract.configuration;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.wavesoftware.eid.utils.EidPreconditions;

import javax.annotation.Nonnull;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static pl.wavesoftware.eid.utils.EidPreconditions.tryToExecute;

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

    private URI serverAddressUri = tryToExecute(new EidPreconditions.UnsafeSupplier<URI>() {
        @Override
        @Nonnull
        public URI get() throws URISyntaxException {
            return new URI("http://cascades.example.org");
        }
    }, "20170330:151356");

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        instanceName = "PESEL";
        configuration = new Configuration(
            driver,
            true,
            160L,
            server,
            migration,
            instanceName,
            serverAddressUri
        );
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
        URI actual = configuration.getCascadesServerUri();

        // then
        assertEquals(serverAddressUri, actual);
    }

}
