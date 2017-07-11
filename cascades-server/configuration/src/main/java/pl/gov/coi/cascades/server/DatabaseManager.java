package pl.gov.coi.cascades.server;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 06.07.17
 */
public interface DatabaseManager {

    JdbcTemplate get(String serverId) throws SQLException;

}
