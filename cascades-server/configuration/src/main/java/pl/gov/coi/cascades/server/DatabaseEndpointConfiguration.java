package pl.gov.coi.cascades.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 10.07.17
 */
@Configuration
@Profile(Environment.PRODUCTION_NAME)
public class DatabaseEndpointConfiguration {

    @Bean
    Map<String, DriverManagerDataSource> produceDriverManagerDataSource(ServerConfigurationService service) {
        Map<String, DriverManagerDataSource> managers = new HashMap<>();
        for (ServerDef serverDef : service.getManagedServers()) {
            ConnectionConfiguration configuration = getConnectionConfiguration(serverDef);
            DriverManagerDataSource manager = new DriverManagerDataSource();
            manager.setDriverClassName(configuration.getDriver());
            manager.setUrl(String.format(
                configuration.getUrl(),
                serverDef.getHost(),
                serverDef.getPort(),
                serverDef.getDbname())
            );
            manager.setPassword(serverDef.getPassword());
            manager.setUsername(serverDef.getUser());
            managers.put(serverDef.getServerId(), manager);
        }
        return managers;
    }

    private static ConnectionConfiguration getConnectionConfiguration(ServerDef serverDef) {
        String driver;
        String url;
        switch (serverDef.getType()) {
            case "ora12c":
                driver = "oracle.jdbc.driver.OracleDriver";
                url = "jdbc:oracle:thin:@//%s:%d/%s";
                break;
            case "pgsql":
                driver = "org.postgresql.Driver";
                url = "jdbc:postgresql://%s:%d/%s";
                break;
            default:
                throw new EidIllegalArgumentException(
                    "20170728:150904",
                    "Given driver hasn't been recognised."
                );
        }
        return new ConnectionConfiguration(
            driver,
            url
        );
    }

    @Bean
    DatabaseManager produceDatabaseManager(Map<String, DriverManagerDataSource> driver) {
        return new DatabaseEndpointManager(driver);
    }

    @Bean
    pl.gov.coi.cascades.server.domain.DatabaseTemplateGateway produceDatabaseTemplateGateway(DatabaseManager manager) {
        return new GeneralTemplateGateway(manager);
    }

    @Getter
    @AllArgsConstructor
    @RequiredArgsConstructor
    private static final class ConnectionConfiguration {
        private String driver;
        private String url;
    }

}
