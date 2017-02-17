package pl.gov.coi.cascades.server;

import pl.gov.coi.cascades.contract.CascadesRestEndpoint;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 17.02.17.
 */
public class PostgreSQLDatabaseCreator implements CascadesRestEndpoint {

    @Override
    public void launchDatabase() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteDatabase(String databaseName) {
        throw new UnsupportedOperationException();
    }
}