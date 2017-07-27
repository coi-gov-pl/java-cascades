package pl.gov.coi.cascades.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import pl.gov.coi.cascades.server.domain.DatabaseTemplateGateway;

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
            DriverManagerDataSource manager = new DriverManagerDataSource();
            manager.setDriverClassName("oracle.jdbc.driver.OracleDriver");
            manager.setUrl(
                String.format("jdbc:oracle:thin:@//%s:%d/%s",
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

    @Bean
    DatabaseManager produceDatabaseManager(Map<String, DriverManagerDataSource> driver) {
        return new DatabaseEndpointManager(driver);
    }

    @Bean
    DatabaseTemplateGateway produceDatabaseTemplateGateway(DatabaseManager manager) {
        return new DatabaseTemplateGatewayImpl(manager);
    }

}
