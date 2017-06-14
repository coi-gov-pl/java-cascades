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

import static org.assertj.core.api.Assertions.assertThat;
import static pl.wavesoftware.eid.utils.EidPreconditions.tryToExecute;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 21.02.17.
 */
public class ConfigurationTest {

    private Configuration configuration;
    private String instanceName;
    private Long timeoutInSeconds;

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
        timeoutInSeconds = 160L;
        configuration = new Configuration(
            driver,
            true,
            timeoutInSeconds,
            server,
            migration,
            instanceName,
            serverAddressUri
        );
    }

    @Test
    public void testGetTimeOutInSeconds() {
        // when
        Long actual = configuration.getTimeoutInSeconds();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(timeoutInSeconds);
    }

    @Test
    public void testGetter() {
        // when
        Driver actual = configuration.getDriver();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(driver);
    }

    @Test
    public void testIsTryToReuse() {
        // when
        boolean actual = configuration.isTryToReuse();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isTrue();
    }

    @Test
    public void testGetServer() {
        // when
        Optional<Server> actual = configuration.getServer();

        // then
        assertThat(actual).isEqualTo(Optional.of(server));
    }

    @Test
    public void testGetMigration() {
        // when
        Optional<Migration> actual = configuration.getMigration();

        // then
        assertThat(actual).isEqualTo(Optional.of(migration));
    }

    @Test
    public void testGetInstanceName() throws Exception {
        // when
        Optional<String> actual = configuration.getInstanceName();

        // then
        assertThat(actual).isEqualTo(Optional.of(instanceName));
    }

    @Test
    public void testGetCascadesServerNetworkBind() throws Exception {
        // when
        URI actual = configuration.getCascadesServerUri();

        // then
        assertThat(actual).isEqualTo(serverAddressUri);
    }

}
