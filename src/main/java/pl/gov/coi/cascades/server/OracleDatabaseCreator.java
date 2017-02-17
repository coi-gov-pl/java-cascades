package pl.gov.coi.cascades.server;

import pl.gov.coi.cascades.contract.CascadesRestEndpoint;

public class OracleDatabaseCreator implements CascadesRestEndpoint {

    @Override
    public void launchDatabase() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteDatabase(String databaseName) {
        throw new UnsupportedOperationException();
    }
}