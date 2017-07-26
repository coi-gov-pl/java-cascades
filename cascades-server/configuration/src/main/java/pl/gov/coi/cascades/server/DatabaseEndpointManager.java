package pl.gov.coi.cascades.server;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 06.07.17
 */
public class DatabaseEndpointManager implements DatabaseManager {

    private final Map<String, DriverManagerDataSource> managers;

    @Inject
    DatabaseEndpointManager(Map<String, DriverManagerDataSource> managers) {
        this.managers = managers;
    }

    @Override
    public JdbcTemplate get(String serverId) throws SQLException {
        DriverManagerDataSource manager = managers.get(serverId);
        return new JdbcTemplate(manager);
    }

}
