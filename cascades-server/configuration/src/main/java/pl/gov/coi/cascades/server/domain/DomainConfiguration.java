package pl.gov.coi.cascades.server.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.gov.coi.cascades.server.domain.launchdatabase.DatabaseIdGeneratorService;
import pl.gov.coi.cascades.server.domain.launchdatabase.UseCase;
import pl.gov.coi.cascades.server.domain.launchdatabase.UseCaseImpl;
import pl.gov.coi.cascades.server.domain.launchdatabase.UsernameAndPasswordCredentialsGeneratorService;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.02.17.
 */
@Configuration
public class DomainConfiguration {

    @Bean
    UseCase produceLaunchNewDatabaseUseCase(TemplateIdGateway templateIdGateway,
                                            DatabaseInstanceGateway databaseInstanceGateway,
                                            UserGateway userGateway,
                                            DatabaseLimitGateway databaseLimitGateway,
                                            DatabaseIdGeneratorService databaseIdGeneratorService,
                                            UsernameAndPasswordCredentialsGeneratorService credentialsGeneratorService,
                                            DatabaseTypeClassNameService databaseTypeClassNameService) {
        return UseCaseImpl.builder()
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
    pl.gov.coi.cascades.server.domain.deletedatabase.UseCase produceDeleteLaunchedDatabaseUseCase(UserGateway userGateway,
                                                                                                  DatabaseIdGateway databaseIdGateway,
                                                                                                  DatabaseInstanceGateway databaseInstanceGateway) {
        return pl.gov.coi.cascades.server.domain.deletedatabase.UseCaseImpl.builder()
            .userGateway(userGateway)
            .databaseIdGateway(databaseIdGateway)
            .databaseInstanceGateway(databaseInstanceGateway)
            .build();
    }

}
