package pl.gov.coi.cascades.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import pl.gov.coi.cascades.server.domain.DatabaseTemplateGateway;

import java.util.Map;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 10.07.17
 */
@Configuration
@Profile(Environment.PRODUCTION_NAME)
public class DatabaseEndpointConfiguration {

    @Bean
    Map<String, DriverManagerDataSource> produceDriverManagerDataSource(DriverManagerDataSourceHelper driverManagerDataSourceHelper) {
        return driverManagerDataSourceHelper.getManagersMap();
    }

    @Bean
    DriverManagerDataSourceProvider produceDriverManagerDataSourceProvider() {
        return new DriverManagerDataSourceProviderImpl();
    }

    @Bean
    DriverManagerDataSourceHelper produceDriverManagerDataSourceHelper(ConnectionConfigurator connectionConfigurator,
                                                                 ServerConfigurationService serverConfigurationService,
                                                                 DriverManagerDataSourceProvider driverManagerDataSourceProvider) {
        return new DriverManagerDataSourceHelper(
            connectionConfigurator,
            serverConfigurationService,
            driverManagerDataSourceProvider
        );
    }

    @Bean
    DatabaseManager produceDatabaseManager(Map<String, DriverManagerDataSource> driverManagerMap,
                                           DriverManagerDataSourceHelper driverManagerDataSourceHelper) {
        return new DatabaseEndpointManager(driverManagerMap, driverManagerDataSourceHelper);
    }

    @Bean
    DatabaseTemplateGateway produceDatabaseTemplateGateway(DatabaseManager manager) {
        return new GeneralTemplateGateway(manager);
    }

    @Bean
    ConnectionConfigurator produceConnectionConfiguration() {
        return new ConnectionConfigurator();
    }

}
