package pl.gov.coi.cascades.server;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 04.08.17
 */
public class ConnectionConfiguratorTest {

    @Mock
    private ServerConfigurationService service;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testGetConnectionConfigurationWhenThereIsError() throws Exception {
        // given
        String serverId = "Baza_dla_testu_C2DEV";
        String type = "mysql";
        String dbname = "C2DEV";
        String user = "C##DEPLOY";
        String password = "ksSdf#231n#dD";
        String host = "172.31.20.24";
        int port = 1521;
        ServerDef serverDef = new ServerDef(
            serverId,
            type,
            dbname,
            user,
            password,
            host,
            port
        );
        ConnectionConfigurator connectionConfigurator = new ConnectionConfigurator();

        // then
        expectedException.expect(EidIllegalArgumentException.class);
        expectedException.expectMessage("20170728:150904");
        expectedException.expectMessage("Given driver hasn't been recognised.");

        // when
        connectionConfigurator.getConnectionConfiguration(serverDef);
    }

    @Test
    public void testGetConnectionConfigurationWhenThereIsOracleDriver() throws Exception {
        // given
        String serverId = "Baza_dla_testu_C2DEV";
        String type = "ora12c";
        String dbname = "C2DEV";
        String user = "C##DEPLOY";
        String password = "ksSdf#231n#dD";
        String host = "172.31.20.24";
        int port = 1521;
        ServerDef serverDef = new ServerDef(
            serverId,
            type,
            dbname,
            user,
            password,
            host,
            port
        );
        ConnectionConfigurator connectionConfigurator = new ConnectionConfigurator();

        // when
        ConnectionConfiguration actual = connectionConfigurator.getConnectionConfiguration(serverDef);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getUrl()).contains("oracle");
    }

    @Test
    public void testGetConnectionConfigurationWhenThereIsPostgresDriver() throws Exception {
        // given
        String serverId = "Baza_dla_testu_postgres";
        String type = "pgsql";
        String dbname = "postgres";
        String user = "postgres";
        String password = "12345678";
        String host = "localhost";
        int port = 1521;
        ServerDef serverDef = new ServerDef(
            serverId,
            type,
            dbname,
            user,
            password,
            host,
            port
        );
        ConnectionConfigurator connectionConfigurator = new ConnectionConfigurator();

        // when
        ConnectionConfiguration actual = connectionConfigurator.getConnectionConfiguration(serverDef);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getUrl()).contains("postgres");
    }

}
