package pl.gov.coi.cascades.server.domain;

import pl.gov.coi.cascades.contract.domain.DatabaseType;

import java.util.function.Consumer;

/**
 * Database type data transfer object.
 * Contains information about a type of database.
 */
public class DatabaseTypeDTO {

    /**
     * Method gives DTO of database type for given consumer of database type.
     *
     * @param databaseTypeConsumer Given consumer of database type.
     * @return DTO of database type.
     */
    public DatabaseTypeDTO onSuccess(Consumer<DatabaseType> databaseTypeConsumer) {
        throw new UnsupportedOperationException();
    }

    /**
     * Method gives DTO of database type for given consumer of error.
     *
     * @param errorConsumer Given consumer of error.
     * @return DTO of database type.
     */
    public DatabaseTypeDTO onFail(Consumer<Error> errorConsumer) {
        throw new UnsupportedOperationException();
    }

    /**
     * Method resolve type of database.
     */
    public void resolve() {
        throw new UnsupportedOperationException();
    }

}
