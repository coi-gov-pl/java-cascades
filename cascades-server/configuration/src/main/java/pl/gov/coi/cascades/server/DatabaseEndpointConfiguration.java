package pl.gov.coi.cascades.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import pl.gov.coi.cascades.server.domain.DatabaseTemplateGateway;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 10.07.17
 */
@Configuration
@Profile(Environment.PRODUCTION_NAME)
public class DatabaseEndpointConfiguration {

    @Value("${cascades.managed-servers[0].user}")
    private String user;

    @Value("${cascades.managed-servers[0].password}")
    private String password;

    @Value("${cascades.managed-servers[0].serverId}")
    private String serverId;

    @Bean
    DriverManagerDataSource produceDriverManagerDataSource() {
        DriverManagerDataSource manager = new DriverManagerDataSource();
        manager.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        manager.setUrl("jdbc:oracle:thin:@//172.31.20.24:1521/C2DEV");
        manager.setPassword(password);
        manager.setUsername(user);
        return manager;
    }

    @Bean
    DatabaseManager produceDatabaseManager(DriverManagerDataSource driver) {
        return new DatabaseEndpointManager(driver);
    }

    @Bean
    DatabaseTemplateGateway produceDatabaseTemplateGateway(DatabaseManager manager) {
        return new DatabaseTemplateGatewayImpl(manager);
    }

}
