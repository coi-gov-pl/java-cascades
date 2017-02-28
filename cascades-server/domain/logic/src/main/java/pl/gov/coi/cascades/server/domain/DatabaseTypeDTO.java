package pl.gov.coi.cascades.server.domain;

import pl.gov.coi.cascades.contract.domain.DatabaseType;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Database type data transfer object.
 * Contains information about a type of database.
 */
class DatabaseTypeDTO {

    @Nullable
    private final DatabaseType databaseType;
    @Nullable
    private final Error error;
    @Nullable
    private Consumer<DatabaseType> databaseTypeConsumer;
    @Nullable
    private Consumer<Error> errorConsumer;

    DatabaseTypeDTO(DatabaseType databaseType) {
        this.databaseType = databaseType;
        error = null;
    }

    DatabaseTypeDTO(Error error) {
        this.error = error;
        databaseType = null;
    }

    /**
     * Method gives DTO of database type for given consumer of database type.
     *
     * @param databaseTypeConsumer Given consumer of database type.
     * @return DTO of database type.
     */
    DatabaseTypeDTO onSuccess(Consumer<DatabaseType> databaseTypeConsumer) {
        this.databaseTypeConsumer = databaseTypeConsumer;
        return this;
    }

    /**
     * Method gives DTO of database type for given consumer of error.
     *
     * @param errorConsumer Given consumer of error.
     * @return DTO of database type.
     */
    DatabaseTypeDTO onFail(Consumer<Error> errorConsumer) {
        this.errorConsumer = errorConsumer;
        return this;
    }

    /**
     * Method resolve type of database.
     */
    void resolve() {
        Optional.ofNullable(databaseType)
            .ifPresent(databaseTypeConsumer);
        Optional.ofNullable(error)
            .ifPresent(errorConsumer);
    }

}
