package pl.gov.coi.cascades.server;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.07.17
 */
@Configuration
@ConfigurationProperties(prefix = "cascades")
public class ServerConfigurationService {

    @Getter
    private List<ServerDef> managedServers = new ArrayList<>();

}
