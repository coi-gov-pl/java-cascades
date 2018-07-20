package pl.gov.coi.cascades.server.domain;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
public interface DatabaseUserGateway {

    /**
     * Creates a new user for the given username, password and adds permissions.
     * For postgres database
     *
     * @param databaseInstance Given databaseInstance data for create user.
     */
    void createUserPostgres(DatabaseInstance databaseInstance);

    /**
     * Deletes user from database.
     *
     * @param databaseInstance Given databaseInstance.
     */
    void deleteUser(DatabaseInstance databaseInstance);
}
