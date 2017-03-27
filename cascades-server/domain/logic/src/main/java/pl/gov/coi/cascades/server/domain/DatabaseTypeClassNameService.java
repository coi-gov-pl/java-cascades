package pl.gov.coi.cascades.server.domain;

import pl.gov.coi.cascades.contract.domain.DatabaseType;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 08.03.17.
 */
@FunctionalInterface
public interface DatabaseTypeClassNameService {
    /**
     * Gets a DatabaseType DTO that encapsulates a found database type or
     * errors that was raised while locating.
     *
     * @param type a type name that consistent with {@link DatabaseType#getName()} or FQCN
     *             (fully qualified call name)
     * @return a returned DTO
     */
    DatabaseTypeDTO getDatabaseType(String type);
}
