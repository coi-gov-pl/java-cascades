package pl.gov.coi.cascades.server.persistance.hibernate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import pl.gov.coi.cascades.server.Environment;
import pl.gov.coi.cascades.server.domain.DatabaseIdGateway;
import pl.gov.coi.cascades.server.domain.DatabaseTypeClassNameService;
import pl.gov.coi.cascades.server.domain.UserGateway;

import javax.inject.Singleton;
import javax.transaction.Transactional;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 02.04.17.
 */
@Configuration
@Profile(Environment.DEVELOPMENT_NAME)
public class DataConfiguration {

    @Bean
    @Singleton
    @Transactional
    @Primary
    UserGateway createUserGateway(DatabaseTypeClassNameService databaseTypeClassNameService) {
        return new UserGatewayImpl(databaseTypeClassNameService);
    }

    @Bean
    @Singleton
    @Transactional
    @Primary
    DatabaseIdGateway createDatabaseIdGateway(DatabaseTypeClassNameService databaseTypeClassNameService) {
        return new DatabaseIdGatewayImpl(databaseTypeClassNameService);
    }

}
