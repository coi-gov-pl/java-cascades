package pl.gov.coi.cascades.server.domain;

public interface DatabaseOperationsGateway {

    /**
     * Create new instance of database.
     *
     * @param databaseInstance Given instance of database.
     * @return DatabaseInstance with connection settings
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
