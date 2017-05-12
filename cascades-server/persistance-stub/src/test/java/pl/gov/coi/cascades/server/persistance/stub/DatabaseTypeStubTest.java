package pl.gov.coi.cascades.server.persistance.stub;

import org.junit.Test;
import pl.gov.coi.cascades.contract.domain.ConnectionStringProducer;
import pl.gov.coi.cascades.contract.domain.NetworkBind;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.04.17.
 */
public class DatabaseTypeStubTest {

    @Test
    public void testGetName() throws Exception {
        // given
        String name = "stub";
        DatabaseTypeStub databaseTypeStub = new DatabaseTypeStub();

        // when
        String actual = databaseTypeStub.getName();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(name);
    }

    @Test
    public void testGetConnectionStringProducer() throws Exception {
        // given
        NetworkBind databaseNetworkBind = DatabaseIdGatewayStub.NETWORK_BIND;
        String databaseName = DatabaseIdGatewayStub.INSTANCE1.getDatabaseName();
        DatabaseTypeStub databaseTypeStub = new DatabaseTypeStub();

        // when
        ConnectionStringProducer connectionStringProducer = databaseTypeStub.getConnectionStringProducer();
        String actual = connectionStringProducer.produce(
            databaseNetworkBind,
            databaseName
        );

        // then
        assertThat(connectionStringProducer).isNotNull();
        assertThat(actual).isNotNull();
        assertThat(actual).contains(
            "db://" +
            databaseNetworkBind.getHost() +
            ":" +
            databaseNetworkBind.getPort() +
            "/" +
            databaseName
        );
    }

}
