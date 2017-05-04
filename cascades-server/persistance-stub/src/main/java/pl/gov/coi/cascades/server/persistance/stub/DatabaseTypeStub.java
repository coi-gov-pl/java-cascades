package pl.gov.coi.cascades.server.persistance.stub;

import pl.gov.coi.cascades.contract.domain.ConnectionStringProducer;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.NetworkBind;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 07.03.17.
 */
public class DatabaseTypeStub implements DatabaseType {

    private static final long serialVersionUID = 42L;

    @Override
    public String getName() {
        return "stub";
    }

    @Override
    public ConnectionStringProducer getConnectionStringProducer() {
        return new ExampleConnectionStringProducer();
    }

    private static final class ExampleConnectionStringProducer implements ConnectionStringProducer {
        @Override
        public String produce(NetworkBind databaseNetworkBind, String databaseName) {
            return String.format(
                "db://%s:%d/%s",
                databaseNetworkBind.getHost(),
                databaseNetworkBind.getPort(),
                databaseName
            );
        }
    }
}
