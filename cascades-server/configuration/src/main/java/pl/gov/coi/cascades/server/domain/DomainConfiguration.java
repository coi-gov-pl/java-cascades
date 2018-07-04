package pl.gov.coi.cascades.server.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.gov.coi.cascades.server.domain.launchdatabase.DatabaseIdGeneratorService;
import pl.gov.coi.cascades.server.domain.launchdatabase.DatabaseNameGeneratorService;
import pl.gov.coi.cascades.server.domain.launchdatabase.LaunchNewDatabaseGatewayFacade;
import pl.gov.coi.cascades.server.domain.launchdatabase.UseCase;
import pl.gov.coi.cascades.server.domain.launchdatabase.UseCaseImpl;
import pl.gov.coi.cascades.server.domain.launchdatabase.UsernameAndPasswordCredentialsGeneratorService;
import pl.gov.coi.cascades.server.domain.loadtemplate.TemplateIdGeneratorService;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.02.17.
 */
@Configuration
public class DomainConfiguration {

    @Bean
    UseCase produceLaunchNewDatabaseUseCase(LaunchNewDatabaseGatewayFacade launchNewDatabaseGatewayFacade,
                                            DatabaseNameGeneratorService databaseNameGeneratorService,
                                            UsernameAndPasswordCredentialsGeneratorService credentialsGeneratorService,
                                            DatabaseTypeClassNameService databaseTypeClassNameService,
                                            DatabaseIdGeneratorService databaseIdGeneratorService) {
        return UseCaseImpl.builder()
            .credentialsGeneratorService(credentialsGeneratorService)
            .databaseNameGeneratorService(databaseNameGeneratorService)
            .launchNewDatabaseGatewayFacade(launchNewDatabaseGatewayFacade)
            .databaseTypeClassNameService(databaseTypeClassNameService)
            .databaseIdGeneratorService(databaseIdGeneratorService)
            .build();
    }

    @Bean
    LaunchNewDatabaseGatewayFacade produceGateways(TemplateIdGateway templateIdGateway,
                                                   UserGateway userGateway,
                                                   DatabaseLimitGateway databaseLimitGateway,
                                                   DatabaseInstanceGateway databaseInstanceGateway,
                                                   DatabaseOperations databaseOperations,
                                                   DatabaseUserGateway databaseUserGateway) {
        return new LaunchNewDatabaseGatewayFacade(
            templateIdGateway,
            userGateway,
            databaseLimitGateway,
            databaseInstanceGateway,
            databaseOperations,
            databaseUserGateway
        );
    }

    @Bean
    pl.gov.coi.cascades.server.domain.deletedatabase.UseCase produceDeleteLaunchedDatabaseUseCase(
        UserGateway userGateway,
        DatabaseIdGateway databaseIdGateway,
        DatabaseInstanceGateway databaseInstanceGateway) {
        return pl.gov.coi.cascades.server.domain.deletedatabase.UseCaseImpl.builder()
            .userGateway(userGateway)
            .databaseIdGateway(databaseIdGateway)
            .databaseInstanceGateway(databaseInstanceGateway)
            .build();
    }

    @Bean
    pl.gov.coi.cascades.server.domain.loadtemplate.UseCase produceLoadTemplateUseCase(
        TemplateIdGateway templateIdGateway,
        DatabaseTemplateGateway databaseTemplateGateway,
        TemplateIdGeneratorService templateIdGeneratorService) {
        return pl.gov.coi.cascades.server.domain.loadtemplate.UseCaseImpl.builder()
            .templateIdGeneratorService(templateIdGeneratorService)
            .databaseTemplateGateway(databaseTemplateGateway)
            .templateIdGateway(templateIdGateway)
            .build();
    }

}
