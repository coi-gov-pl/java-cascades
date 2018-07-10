package pl.gov.coi.cascades.server;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
public class NetworkBindImplTest {

    private static final int PORT = 8080;
    private static final String HOST = "localhost";

    private NetworkBindImpl networkBind;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        networkBind = new NetworkBindImpl(
            HOST,
            PORT
        );
    }

    @Test
    public void shouldGetHost() {
        // when
        String actual = networkBind.getHost();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo("localhost");
    }

    @Test
    public void shouldSetHost() {
        // given
        String host = "db.internal";

        // when
        networkBind.setHost(host);
        String actual = networkBind.getHost();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo("db.internal");
    }

    @Test
    public void shouldGetPort() {
        // when
        int actual = networkBind.getPort();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(8080);
    }

    @Test
    public void shouldSetPort() {
        // given
        int port = 1234;

        // when
        networkBind.setPort(port);
        int actual = networkBind.getPort();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(1234);
    }
}
