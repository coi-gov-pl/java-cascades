package pl.gov.coi.cascades.server;

import lombok.Data;
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

    private static final int DEFAULT_PORT = 1521;

    @Value("${cascades.managed-servers[0].serverId}")
    private String serverId;

    @Value("${cascades.managed-servers[0].user}")
    private String user;

    @Value("${cascades.managed-servers[0].password}")
    private String password;

    @Value("${cascades.managed-servers[0].dbname}")
    private String dbname;

    @Value("${cascades.managed-servers[0].host}")
    private String host;

    @Value("${cascades.managed-servers[0].port}")
    private int port = DEFAULT_PORT;

    @Bean
    DriverManagerDataSource produceDriverManagerDataSource() {
        DriverManagerDataSource manager = new DriverManagerDataSource();
        manager.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        manager.setUrl(String.format("jdbc:oracle:thin:@//%s:%d/%s", host, port, dbname));
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

    @Data
    private static final class ServerDef {
        private String serverId;
        private String user;
        private String password;
        private String dbname;
        private String host;
        private int port = DEFAULT_PORT;
    }

}
