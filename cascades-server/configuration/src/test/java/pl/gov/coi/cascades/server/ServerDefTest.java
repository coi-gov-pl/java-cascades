package pl.gov.coi.cascades.server;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.07.17
 */
public class ServerDefTest {

    @Test
    public void testConstructorWithParameters() {
        // when
        ServerDef serverDef = new ServerDef(
            "test",
            "db_name",
            "admin",
            "pass123",
            "172.0.0.1",
            1234
        );
        // then
        assertThat(serverDef).isNotNull();
    }

    @Test
    public void testEquals() throws Exception {
        // given
        ServerDef serverDef = new ServerDef();
        ServerDef otherServerDef = new ServerDef();
        otherServerDef.setPort(1234);

        // when
        boolean actual = serverDef.equals(otherServerDef);

        // then
        assertThat(actual).isFalse();
    }

    @Test
    public void testHashCode() throws Exception {
        // given
        ServerDef serverDef = new ServerDef();
        ServerDef otherServerDef = new ServerDef();

        // when
        int actual = serverDef.hashCode();
        int other = otherServerDef.hashCode();

        // then
        assertThat(actual).isEqualTo(other);
    }

    @Test
    public void testCanEqual() throws Exception {
        // given
        ServerDef serverDef = new ServerDef();
        ServerDef otherServerDef = new ServerDef();

        // when
        boolean actual = serverDef.canEqual(otherServerDef);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    public void testToString() throws Exception {
        // given
        ServerDef serverDef = new ServerDef();
        serverDef.setServerId("test");
        serverDef.setDbname("db_name");
        serverDef.setServerId("test");
        serverDef.setPassword("pass");
        serverDef.setHost("172.0.0.1");
        serverDef.setUser("admin");
        serverDef.setPort(1234);

        // when
        String actual = serverDef.toString();

        // then
        assertThat(actual).contains("serverId=test");
        assertThat(actual).contains("dbname=db_name");
        assertThat(actual).contains("user=admin");
        assertThat(actual).contains("password=pass");
        assertThat(actual).contains("host=172.0.0.1");
        assertThat(actual).contains("port=1234");
    }

    @Test
    public void testSetServerId() throws Exception {
        // given
        ServerDef serverDef = new ServerDef();

        // when
        serverDef.setServerId("test");

        // then
        assertThat(serverDef.getServerId()).isEqualTo("test");
    }

    @Test
    public void testSetDbname() throws Exception {
        // given
        ServerDef serverDef = new ServerDef();

        // when
        serverDef.setDbname("db_name");

        // then
        assertThat(serverDef.getDbname()).isEqualTo("db_name");
    }

    @Test
    public void testSetUser() throws Exception {
        // given
        ServerDef serverDef = new ServerDef();

        // when
        serverDef.setServerId("test");

        // then
        assertThat(serverDef.getServerId()).isEqualTo("test");
    }

    @Test
    public void testSetPassword() throws Exception {
        // given
        ServerDef serverDef = new ServerDef();

        // when
        serverDef.setPassword("pass");

        // then
        assertThat(serverDef.getPassword()).isEqualTo("pass");
    }

    @Test
    public void testSetHost() throws Exception {
        // given
        ServerDef serverDef = new ServerDef();

        // when
        serverDef.setHost("172.0.0.1");

        // then
        assertThat(serverDef.getHost()).isEqualTo("172.0.0.1");
    }

    @Test
    public void testSetPort() throws Exception {
        // given
        ServerDef serverDef = new ServerDef();

        // when
        serverDef.setPort(1234);

        // then
        assertThat(serverDef.getPort()).isEqualTo(1234);
    }

    @Test
    public void testGetServerId() throws Exception {
        // given
        ServerDef serverDef = new ServerDef();

        // when
        serverDef.setHost("172.0.0.1");

        // then
        assertThat(serverDef.getHost()).isEqualTo("172.0.0.1");
    }

    @Test
    public void testGetDbname() throws Exception {
        // given
        ServerDef serverDef = new ServerDef();
        serverDef.setDbname("db_name");

        // when
        String actual = serverDef.getDbname();

        // then
        assertThat(actual).isEqualTo("db_name");
    }

    @Test
    public void testGetUser() throws Exception {
        // given
        ServerDef serverDef = new ServerDef();
        serverDef.setUser("admin");

        // when
        String actual = serverDef.getUser();

        // then
        assertThat(actual).isEqualTo("admin");
    }

    @Test
    public void testGetPassword() throws Exception {
        // given
        ServerDef serverDef = new ServerDef();
        serverDef.setPassword("pass123");

        // when
        String actual = serverDef.getPassword();

        // then
        assertThat(actual).isEqualTo("pass123");
    }

    @Test
    public void testGetHost() throws Exception {
        // given
        ServerDef serverDef = new ServerDef();
        serverDef.setHost("172.0.0.1");

        // when
        String actual = serverDef.getHost();

        // then
        assertThat(actual).isEqualTo("172.0.0.1");
    }

    @Test
    public void testGetPort() throws Exception {
        // given
        ServerDef serverDef = new ServerDef();
        serverDef.setPort(1234);

        // when
        int actual = serverDef.getPort();

        // then
        assertThat(actual).isEqualTo(1234);
    }

}
