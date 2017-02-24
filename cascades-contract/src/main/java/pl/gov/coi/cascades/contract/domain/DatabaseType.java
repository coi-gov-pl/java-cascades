package pl.gov.coi.cascades.contract.domain;

/**
 * A type of the database
 */
public interface DatabaseType {

    /**
     * Method gives name of database type.
     *
     * @return Name of database type.
     */
    String getName();

    /**
     * Gets a connection string producer
     * @return a connection string producer
     */
    ConnectionStringProducer getConnectionStringProducer();
}
