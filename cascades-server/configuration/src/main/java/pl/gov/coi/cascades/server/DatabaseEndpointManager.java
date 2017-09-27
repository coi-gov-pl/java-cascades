package pl.gov.coi.cascades.server;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;

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
    public ConnectionDatabase get(String serverId) throws SQLException {
        DriverManagerDataSource manager = managers.get(serverId);

        if (manager == null) {
            throw new EidIllegalArgumentException(
                "20170726:121616",
                "Given serverId hasn't been found."
            );
        }

        return new ConnectionDatabase(
            new JdbcTemplate(manager),
            manager.getUrl()
        );
    }

}
