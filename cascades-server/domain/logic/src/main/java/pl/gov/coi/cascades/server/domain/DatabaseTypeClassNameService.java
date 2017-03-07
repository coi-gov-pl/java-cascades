package pl.gov.coi.cascades.server.domain;

import pl.gov.coi.cascades.contract.domain.DatabaseType;

/**
 * Service for database class type name.
 */
public class DatabaseTypeClassNameService {

    /**
     * Method gives DTO of database type for given name of class type.
     *
     * @param typeClassName Given name of class type.
     */
    public DatabaseTypeDTO getDatabaseType(String typeClassName) {
        DatabaseType databaseType = new DatabaseTypeImpl(typeClassName);
        return new DatabaseTypeDTO(databaseType);
    }

}
