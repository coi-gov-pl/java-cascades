package pl.gov.coi.cascades.plugin.postgresql;

import pl.gov.coi.cascades.contract.domain.ConnectionStringProducer;
import pl.gov.coi.cascades.contract.domain.NetworkBind;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 26.06.18
 */
public class PostgresConnectionStringProducer implements ConnectionStringProducer {
    @Override
    public String produce(
        NetworkBind databaseNetworkBind,
        String databaseName
    ) {
        // TODO: Not yet implemented
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
