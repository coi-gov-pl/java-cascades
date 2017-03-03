package pl.gov.coi.cascades.server.domain;

import pl.gov.coi.cascades.contract.domain.DatabaseId;

/**
 * Class for generating id for databases.
 */
public class DatabaseIdGeneratorService {

    /**
     * Method gives id of database for given name of database instance.
     *
     * @param instanceName Given name of database instance.
     * @return Id of database.
     */
    public DatabaseId generate(String instanceName) {
        throw new UnsupportedOperationException();
    }

    /**
     * Method gives random id of database.
     *
     * @return Id of database.
     */
    public DatabaseId generate() {
        throw new UnsupportedOperationException();
    }

}
