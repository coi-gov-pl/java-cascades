package pl.gov.coi.cascades.server.domain;

public interface DatabaseInstanceGateway {

    /**
     * Method gives instance of database for given instance of database.
     *
     * @param databaseInstance Given instance of database.
     * @return Instance of database.
     */
    DatabaseInstance save(DatabaseInstance databaseInstance);

    void deleteDatabase(DatabaseInstance databaseInstance);

    String getRemoteServerId();

}
