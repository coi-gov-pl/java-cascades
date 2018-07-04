package pl.gov.coi.cascades.server;

import java.sql.SQLException;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 06.07.17
 */
public interface DatabaseManager {

    /**
     * Returns connection to database with sepcified serverId.
     * @param serverId server ID
     * @return connection to database
     */
    ConnectionDatabase getConnectionToServer(String serverId) throws SQLException;

    /**
     * Returns connection to template in database with sepcified serverId.
     * @param serverId server ID
     * @param templateName template name
     * @return connection to database
     */
    ConnectionDatabase getConnectionToTemplate(String serverId, String templateName) throws SQLException;

}
