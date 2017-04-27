package pl.gov.coi.cascades.server.persistance.stub;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.04.17.
 */
public class NetworkBindStubTest {

    private static final int PORT = 8080;
    private static final String HOST = "localhost";
    private NetworkBindStub networkBindStub;

    @Before
    public void setUp() {
        networkBindStub = new NetworkBindStub(
            PORT,
            HOST
        );
    }

    @Test
    public void testGetHost() throws Exception {
        // when
        String actual = networkBindStub.getHost();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(HOST);
    }

    @Test
    public void testSetHost() throws Exception {
        // given
        String host = "db.internal";

        // when
        networkBindStub.setHost(host);
        String actual = networkBindStub.getHost();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(host);
    }

    @Test
    public void testGetPort() throws Exception {
        // when
        int actual = networkBindStub.getPort();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(PORT);
    }

    @Test
    public void testSetPort() throws Exception {
        // given
        int port = 1234;

        // when
        networkBindStub.setPort(port);
        int actual = networkBindStub.getPort();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(port);
    }

    @Test
    public void testBuilder() throws Exception {
        // when
        String host = "db.internal";
        int port = 1234;
        NetworkBindStub networkBindStubBuilder = NetworkBindStub.builder()
            .host(host)
            .port(port)
            .build();

        // then
        assertThat(networkBindStubBuilder).isNotNull();
    }

}
