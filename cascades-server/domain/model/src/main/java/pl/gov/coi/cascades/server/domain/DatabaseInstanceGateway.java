package pl.gov.coi.cascades.server.domain;

public interface DatabaseInstanceGateway {

    /**
     * Method gives instance of database for given instance of database.
     *
     * @param databaseInstance Given instance of database.
     * @return Instance of database.
     */
    DatabaseInstance launchDatabase(DatabaseInstance databaseInstance);

}
