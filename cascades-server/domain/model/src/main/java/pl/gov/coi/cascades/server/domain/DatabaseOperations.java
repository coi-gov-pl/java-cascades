package pl.gov.coi.cascades.server.domain;

import pl.gov.coi.cascades.contract.domain.NetworkBind;

public interface DatabaseOperations {

    /**
     * Create new instance of database.
     *
     * @param databaseInstance Given instance of database.
     * @return NetworkBind with connection settings
     */
    DatabaseInstance createDatabase(DatabaseInstance databaseInstance);

    /**
     * Delete database.
     *
     * @param databaseInstance Given instance of database.
     * @return NetworkBind with connection settings
     */
    void deleteDatabase(DatabaseInstance databaseInstance);
}
