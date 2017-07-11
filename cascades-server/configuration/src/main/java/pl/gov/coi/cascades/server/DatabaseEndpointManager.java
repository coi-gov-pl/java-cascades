package pl.gov.coi.cascades.server;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.inject.Inject;
import java.sql.SQLException;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 06.07.17
 */
public class DatabaseEndpointManager implements DatabaseManager {

    private final DriverManagerDataSource manager;

    @Inject
    DatabaseEndpointManager(DriverManagerDataSource manager) {
        this.manager = manager;
    }

    @Override
    public JdbcTemplate get(String serverId) throws SQLException {
        return new JdbcTemplate(manager);
    }

}
