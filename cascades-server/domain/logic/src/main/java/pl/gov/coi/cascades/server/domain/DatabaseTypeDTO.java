package pl.gov.coi.cascades.server.domain;

import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.service.Violation;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Database type data transfer object.
 * Contains information about a type of database.
 */
public class DatabaseTypeDTO {

    @Nullable
    private final DatabaseType databaseType;
    @Nullable
    private final Violation violation;
    @Nullable
    private Consumer<DatabaseType> databaseTypeConsumer;
    @Nullable
    private Consumer<Violation> errorConsumer;

    protected DatabaseTypeDTO(DatabaseType databaseType) {
        this.databaseType = databaseType;
        violation = null;
    }

    DatabaseTypeDTO(Violation violation) {
        this.violation = violation;
        databaseType = null;
    }

    /**
     * Method gives DTO of database type for given consumer of database type.
     *
     * @param databaseTypeConsumer Given consumer of database type.
     * @return DTO of database type.
     */
    public DatabaseTypeDTO onSuccess(Consumer<DatabaseType> databaseTypeConsumer) {
        this.databaseTypeConsumer = databaseTypeConsumer;
        return this;
    }

    /**
     * Method gives DTO of database type for given consumer of error.
     *
     * @param errorConsumer Given consumer of error.
     * @return DTO of database type.
     */
    public DatabaseTypeDTO onFail(Consumer<Violation> errorConsumer) {
        this.errorConsumer = errorConsumer;
        return this;
    }

    /**
     * Method resolve type of database.
     */
    public void resolve() {
        Optional.ofNullable(databaseType)
            .ifPresent(databaseTypeConsumer);
        Optional.ofNullable(violation)
            .ifPresent(errorConsumer);
    }

}
