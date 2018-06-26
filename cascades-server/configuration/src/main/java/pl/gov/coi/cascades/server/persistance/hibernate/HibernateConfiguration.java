package pl.gov.coi.cascades.server.persistance.hibernate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.gov.coi.cascades.server.ProfileType;
import pl.gov.coi.cascades.server.domain.DatabaseIdGateway;
import pl.gov.coi.cascades.server.domain.DatabaseInstanceGateway;
import pl.gov.coi.cascades.server.domain.DatabaseLimitGateway;
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
    UserGateway createUserGateway(DatabaseTypeClassNameService databaseTypeClassNameService) {
        return new UserGatewayImpl(
            new UserMapper(databaseTypeClassNameService)
        );
    }

    @Bean
    DatabaseIdGateway createDatabaseIdGateway(DatabaseTypeClassNameService databaseTypeClassNameService) {
        return new DatabaseIdGatewayImpl(
            new DatabaseInstanceMapper(databaseTypeClassNameService)
        );
    }

    @Bean
    DatabaseInstanceGateway createDatabaseInstanceGateway(DatabaseTypeClassNameService databaseTypeClassNameService) {
        return new DatabaseInstanceGatewayImpl(
            new DatabaseInstanceMapper(databaseTypeClassNameService)
        );
    }

    @Bean
    DatabaseLimitGateway createDatabaseLimitGateway(DatabaseTypeClassNameService databaseTypeClassNameService) {
        return new DatabaseLimitGatewayImpl(
            new DatabaseInstanceMapper(databaseTypeClassNameService)
        );
    }
}
