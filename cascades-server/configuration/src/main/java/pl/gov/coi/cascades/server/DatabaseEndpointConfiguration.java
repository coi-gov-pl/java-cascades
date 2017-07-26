package pl.gov.coi.cascades.server;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import pl.gov.coi.cascades.server.domain.DatabaseTemplateGateway;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 10.07.17
 */
@Configuration
@ConfigurationProperties(prefix = "cascades")
@Profile(Environment.PRODUCTION_NAME)
@RequiredArgsConstructor
public class DatabaseEndpointConfiguration {

    private static final int DEFAULT_PORT = 1521;

    @Value("${cascades.managedServers[0].serverId}")
    private String serverId;

    @Value("${cascades.managedServers[0].dbname}")
    private String dbname;

    @Value("${cascades.managedServers[0].user}")
    private String user;

    @Value("${cascades.managedServers[0].password}")
    private String password;

    @Value("${cascades.managedServers[0].host}")
    private String host;

    @Value("${cascades.managedServers[0].port}")
    private int port = DEFAULT_PORT;

    @Getter
    private List<ServerDef> managedServers = new ArrayList<>();

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

}
