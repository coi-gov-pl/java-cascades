package pl.gov.coi.cascades.server.domain;

import pl.gov.coi.cascades.contract.domain.ConnectionStringProducer;
import pl.gov.coi.cascades.contract.domain.DatabaseType;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 07.03.17.
 */
public class DatabaseTypeImpl implements DatabaseType {

    private final String name;
    private ConnectionStringProducer connectionStringProducer;

    public DatabaseTypeImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ConnectionStringProducer getConnectionStringProducer() {
        return connectionStringProducer;
    }
}
