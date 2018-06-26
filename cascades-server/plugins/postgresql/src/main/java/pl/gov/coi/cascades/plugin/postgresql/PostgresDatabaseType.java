package pl.gov.coi.cascades.plugin.postgresql;

import pl.gov.coi.cascades.contract.domain.ConnectionStringProducer;
import pl.gov.coi.cascades.contract.domain.DatabaseType;

import javax.inject.Inject;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 26.06.18
 */
public class PostgresDatabaseType implements DatabaseType {

    private final ConnectionStringProducer producer;

    @Inject
    public PostgresDatabaseType(ConnectionStringProducer producer) {
        this.producer = producer;
    }

    @Override
    public String getName() {
        return "PostgreSQL";
    }

    @Override
    public ConnectionStringProducer getConnectionStringProducer() {
        return producer;
    }
}
