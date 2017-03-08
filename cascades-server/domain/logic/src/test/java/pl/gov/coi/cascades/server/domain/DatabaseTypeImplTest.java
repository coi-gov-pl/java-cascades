package pl.gov.coi.cascades.server.domain;

import pl.gov.coi.cascades.contract.domain.ConnectionStringProducer;
import pl.gov.coi.cascades.contract.domain.DatabaseType;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 08.03.17.
 */
public class DatabaseTypeImplTest implements DatabaseType {

    private final String name;

    public DatabaseTypeImplTest(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ConnectionStringProducer getConnectionStringProducer() {
        return null;
    }
}
