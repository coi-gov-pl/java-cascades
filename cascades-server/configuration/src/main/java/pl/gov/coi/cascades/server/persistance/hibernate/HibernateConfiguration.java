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

import javax.transaction.Transactional;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 02.04.17.
 */
@Configuration
@Profile(ProfileType.HIBERNATE_NAME)
public class HibernateConfiguration {

    @Bean
    @Transactional
    TemplateIdGateway createTemplateIdGateway() {
        return new TemplateIdGatewayImpl();
    }

    @Bean
    @Transactional
    UserGateway createUserGateway(DatabaseTypeClassNameService databaseTypeClassNameService) {
        return new UserGatewayImpl(databaseTypeClassNameService);
    }

    @Bean
    @Transactional
    DatabaseIdGateway createDatabaseIdGateway(DatabaseTypeClassNameService databaseTypeClassNameService) {
        return new DatabaseIdGatewayImpl(
            databaseTypeClassNameService
        );
    }

    @Bean
    @Transactional
    DatabaseInstanceGateway createDatabaseInstanceGateway() {
        return new DatabaseInstanceGatewayImpl();
    }

    @Bean
    @Transactional
    DatabaseLimitGateway createDatabaseLimitGateway() {
        return new DatabaseLimitGatewayImpl();
    }

}
