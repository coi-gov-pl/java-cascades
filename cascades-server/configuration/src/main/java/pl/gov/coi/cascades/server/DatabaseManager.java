package pl.gov.coi.cascades.server;

import java.sql.SQLException;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 06.07.17
 */
public interface DatabaseManager {

    ConnectionDatabase get(String serverId) throws SQLException;

}
