package pl.gov.coi.cascades.server.persistance.hibernate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.gov.coi.cascades.server.DatabaseOperationsImpl;
import pl.gov.coi.cascades.server.ProfileType;
import pl.gov.coi.cascades.server.ServerConfigurationService;
import pl.gov.coi.cascades.server.domain.DatabaseIdGateway;
import pl.gov.coi.cascades.server.domain.DatabaseInstanceGateway;
import pl.gov.coi.cascades.server.domain.DatabaseLimitGateway;
import pl.gov.coi.cascades.server.domain.DatabaseOperations;
import pl.gov.coi.cascades.server.domain.DatabaseTypeClassNameService;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;
import pl.gov.coi.cascades.server.domain.UserGateway;
import pl.gov.coi.cascades.server.persistance.hibernate.mapper.DatabaseInstanceMapper;
import pl.gov.coi.cascades.server.persistance.hibernate.mapper.UserMapper;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 02.04.17.
 */
@Configuration
@Profile(ProfileType.HIBERNATE_NAME)
public class HibernateConfiguration {

    @Bean
    TemplateIdGateway createTemplateIdGateway() {
        return new TemplateIdGatewayImpl();
    }

    @Bean
    UserGateway createUserGateway(UserMapper userMapper) {
        return new UserGatewayImpl(userMapper);
    }

    @Bean
    DatabaseIdGateway createDatabaseIdGateway(DatabaseInstanceMapper databaseInstanceMapper) {
        return new DatabaseIdGatewayImpl(databaseInstanceMapper);
    }

    @Bean
    DatabaseInstanceGateway createDatabaseInstanceGateway(DatabaseInstanceMapper databaseInstanceMapper) {
        return new DatabaseInstanceGatewayImpl(databaseInstanceMapper);
    }

    @Bean
    DatabaseLimitGateway createDatabaseLimitGateway(DatabaseInstanceMapper databaseInstanceMapper) {
        return new DatabaseLimitGatewayImpl(databaseInstanceMapper);
    }

    @Bean
    UserMapper createUserMapper(DatabaseTypeClassNameService databaseTypeClassNameService) {
        return new UserMapper(databaseTypeClassNameService);
    }

    @Bean
    DatabaseInstanceMapper createDatabaseInstanceMapper(DatabaseTypeClassNameService databaseTypeClassNameService) {
        return new DatabaseInstanceMapper(databaseTypeClassNameService);
    }

    @Bean
    DatabaseOperations createDatabaseOperations(ServerConfigurationService serverConfigurationService,
                                                TemplateIdGateway templateIdGateway) {
        return new DatabaseOperationsImpl(serverConfigurationService, templateIdGateway);
    }
}
