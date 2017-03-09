package pl.gov.coi.cascades.server.presentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.gov.coi.cascades.server.domain.DatabaseIdGeneratorService;
import pl.gov.coi.cascades.server.domain.DatabaseTypeClassNameService;
import pl.gov.coi.cascades.server.domain.DatabaseTypeClassNameServiceImpl;
import pl.gov.coi.cascades.server.domain.UsernameAndPasswordCredentialsGeneratorService;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 02.03.17.
 */
@Configuration
public class PresentationConfiguration {

    @Bean
    DatabaseIdGeneratorService produceDatabaseIdGeneratorService() {
        return new DatabaseIdGeneratorService();
    }

    @Bean
    UsernameAndPasswordCredentialsGeneratorService produceCredentials() {
        return new UsernameAndPasswordCredentialsGeneratorService();
    }

    @Bean
    DatabaseTypeClassNameService produceDatabaseTypeClassName() {
        return new DatabaseTypeClassNameServiceImpl();
    }

}
