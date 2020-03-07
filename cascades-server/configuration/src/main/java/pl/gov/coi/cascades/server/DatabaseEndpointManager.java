package pl.gov.coi.cascades.server;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;

import javax.inject.Inject;
import java.util.Map;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 06.07.17
 */
public class DatabaseEndpointManager implements DatabaseManager {

    private final Map<String, DriverManagerDataSource> managers;
    private final DriverManagerDataSourceHelper driverManagerDataSourceHelper;

    @Inject
    DatabaseEndpointManager(Map<String, DriverManagerDataSource> managers,
                            DriverManagerDataSourceHelper driverManagerDataSourceHelper) {
        this.managers = managers;
        this.driverManagerDataSourceHelper = driverManagerDataSourceHelper;
    }

    @Override
    public ConnectionDatabase getConnectionToServer(String serverId) {
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

    @Override
    public ConnectionDatabase getConnectionToDatabase(String serverId, String templateName) {
        DriverManagerDataSource manager = driverManagerDataSourceHelper.getManager(serverId, templateName);
        return new ConnectionDatabase(
            new JdbcTemplate(manager),
            manager.getUrl()
        );
    }

}
