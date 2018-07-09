package pl.gov.coi.cascades.server;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author <a href="mailto:mariusz.wyszomierski@coi.gov.pl">Mariusz Wyszomierski</a>
 */
@AllArgsConstructor
public class DriverManagerDataSourceHelper {

    private final ConnectionConfigurator connectionConfigurator;
    private final ServerConfigurationService serverConfigurationService;
    private final DriverManagerDataSourceProvider driverManagerDataSourceProvider;

    /**
     * Returns connection to database with given database name on server with given serverId
     * defined in application configuration {@link ServerConfigurationService}.
     *
     * @param serverId server ID
     * @param databaseName database name
     * @return {@link DriverManagerDataSource}
     */
    DriverManagerDataSource getManager(String serverId, String databaseName) {
        ServerDef serverConfiguration = getServerDef(serverId);
        ConnectionConfiguration connectionConfiguration = connectionConfigurator.getConnectionConfiguration(serverConfiguration.getType());
        ServerDef newServerConfiguration = serverConfiguration.getWithNewDatabaseName(databaseName);
        return createDriverManagerDataSource(connectionConfiguration, newServerConfiguration);
    }

    /**
     * Returns map of connections to servers defined in application configuration {@link ServerConfigurationService}.
     * @return map of connections, serverId is the key
     */
    Map<String, DriverManagerDataSource> getManagersMap() {
        Map<String, DriverManagerDataSource> managers = new HashMap<>();
        for (ServerDef serverDef : serverConfigurationService.getManagedServers()) {
            ConnectionConfiguration configuration = connectionConfigurator.getConnectionConfiguration(serverDef.getType());
            DriverManagerDataSource manager = createDriverManagerDataSource(configuration, serverDef);
            managers.put(serverDef.getServerId(), manager);
        }
        return managers;
    }

    private DriverManagerDataSource createDriverManagerDataSource(ConnectionConfiguration connectionConfiguration,
                                                                  ServerDef serverConfiguration) {
        DriverManagerDataSource manager = driverManagerDataSourceProvider.produce();
        manager.setDriverClassName(connectionConfiguration.getDriverClass());
        manager.setUrl(String.format(
            connectionConfiguration.getJdbcUrlTemplate(),
            serverConfiguration.getHost(),
            serverConfiguration.getPort(),
            serverConfiguration.getDbname())
        );
        manager.setPassword(serverConfiguration.getPassword());
        manager.setUsername(serverConfiguration.getUser());
        return manager;
    }

    private ServerDef getServerDef(String serverId) {
        Optional<ServerDef> matchingServerConfiguration = serverConfigurationService.getManagedServers().stream()
            .filter(server -> serverId.equals(server.getServerId()))
            .findFirst();
        if (!matchingServerConfiguration.isPresent()) {
            String errorMessage = String.format("There is no configuration for serverId: %s", serverId);
            throw new EidIllegalStateException("20180625:205951", errorMessage);
        }
        return matchingServerConfiguration.get();
    }
}
