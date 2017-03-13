package pl.gov.coi.cascades.server.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.gov.coi.cascades.server.domain.deletedatabase.DeleteLaunchedDatabaseInstanceUseCase;
import pl.gov.coi.cascades.server.domain.deletedatabase.DeleteLaunchedDatabaseInstanceUseCaseImpl;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.02.17.
 */
@Configuration
public class DomainConfiguration {

    @Bean
    LaunchNewDatabaseInstanceUseCase produceLaunchNewDatabaseUseCase(TemplateIdGateway templateIdGateway,
                                                                     DatabaseInstanceGateway databaseInstanceGateway,
                                                                     UserGateway userGateway,
                                                                     DatabaseLimitGateway databaseLimitGateway,
                                                                     DatabaseIdGeneratorService databaseIdGeneratorService,
                                                                     UsernameAndPasswordCredentialsGeneratorService credentialsGeneratorService,
                                                                     DatabaseTypeClassNameService databaseTypeClassNameService) {
        return LaunchNewDatabaseInstanceUseCaseImpl.builder()
            .credentialsGeneratorService(credentialsGeneratorService)
            .databaseIdGeneratorService(databaseIdGeneratorService)
            .databaseInstanceGateway(databaseInstanceGateway)
            .databaseLimitGateway(databaseLimitGateway)
            .templateIdGateway(templateIdGateway)
            .userGateway(userGateway)
            .databaseTypeClassNameService(databaseTypeClassNameService)
            .build();
    }

    @Bean
    DeleteLaunchedDatabaseInstanceUseCase produceDeleteLaunchedDatabaseUseCase(UserGateway userGateway,
                                                                               DatabaseIdGateway databaseIdGateway,
                                                                               DatabaseInstanceGateway databaseInstanceGateway) {
        return DeleteLaunchedDatabaseInstanceUseCaseImpl.builder()
            .userGateway(userGateway)
            .databaseIdGateway(databaseIdGateway)
            .databaseInstanceGateway(databaseInstanceGateway)
            .build();
    }

}
