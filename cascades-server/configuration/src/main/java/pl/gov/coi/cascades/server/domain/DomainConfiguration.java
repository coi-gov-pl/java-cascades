package pl.gov.coi.cascades.server.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.gov.coi.cascades.server.domain.launchdatabase.DatabaseIdGeneratorService;
import pl.gov.coi.cascades.server.domain.launchdatabase.DatabaseInstanceUseCase;
import pl.gov.coi.cascades.server.domain.launchdatabase.DatabaseInstanceUseCaseImpl;
import pl.gov.coi.cascades.server.domain.launchdatabase.UsernameAndPasswordCredentialsGeneratorService;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.02.17.
 */
@Configuration
public class DomainConfiguration {

    @Bean
    DatabaseInstanceUseCase produceLaunchNewDatabaseUseCase(TemplateIdGateway templateIdGateway,
                                                            DatabaseInstanceGateway databaseInstanceGateway,
                                                            UserGateway userGateway,
                                                            DatabaseLimitGateway databaseLimitGateway,
                                                            DatabaseIdGeneratorService databaseIdGeneratorService,
                                                            UsernameAndPasswordCredentialsGeneratorService credentialsGeneratorService,
                                                            DatabaseTypeClassNameService databaseTypeClassNameService) {
        return DatabaseInstanceUseCaseImpl.builder()
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
    pl.gov.coi.cascades.server.domain.deletedatabase.DatabaseInstanceUseCase produceDeleteLaunchedDatabaseUseCase(UserGateway userGateway,
                                                                                                                  DatabaseIdGateway databaseIdGateway,
                                                                                                                  DatabaseInstanceGateway databaseInstanceGateway) {
        return pl.gov.coi.cascades.server.domain.deletedatabase.DatabaseInstanceUseCaseImpl.builder()
            .userGateway(userGateway)
            .databaseIdGateway(databaseIdGateway)
            .databaseInstanceGateway(databaseInstanceGateway)
            .build();
    }

}
