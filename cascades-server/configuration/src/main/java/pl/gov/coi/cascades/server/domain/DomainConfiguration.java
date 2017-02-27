package pl.gov.coi.cascades.server.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.02.17.
 */
@Configuration
public class DomainConfiguration {

    @Bean
    LaunchNewDatabaseInstanceUseCase produceLaunchNewDatabaseUseCase(TemplateIdGateway templateIdGateway,
                                                                     DatabaseInstanceGateway databaseInstanceGateway,
                                                                     UserGateway userGateway) {
        return new LaunchNewDatabaseInstanceUseCaseImpl(
            templateIdGateway,
            databaseInstanceGateway,
            userGateway
        );
    }
}
