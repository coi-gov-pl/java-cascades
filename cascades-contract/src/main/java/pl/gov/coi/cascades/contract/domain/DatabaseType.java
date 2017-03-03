package pl.gov.coi.cascades.contract.domain;

import java.io.Serializable;

/**
 * A type of the database
 */
public interface DatabaseType extends Serializable {

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
