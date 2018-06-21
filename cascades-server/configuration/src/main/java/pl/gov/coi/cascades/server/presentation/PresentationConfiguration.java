package pl.gov.coi.cascades.server.presentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.gov.coi.cascades.server.OsgiBeanLocator;
import pl.gov.coi.cascades.server.domain.launchdatabase.DatabaseIdGeneratorService;
import pl.gov.coi.cascades.server.domain.launchdatabase.DatabaseNameGeneratorService;
import pl.gov.coi.cascades.server.domain.DatabaseTypeClassNameService;
import pl.gov.coi.cascades.server.domain.OsgiDatabaseTypeClassNameService;
import pl.gov.coi.cascades.server.domain.launchdatabase.UsernameAndPasswordCredentialsGeneratorService;
import pl.gov.coi.cascades.server.domain.loadtemplate.TemplateIdGeneratorService;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 02.03.17.
 */
@Configuration
public class PresentationConfiguration {

    @Bean
    TemplateIdGeneratorService produceTemplateIdGeneratorService() {
        return new TemplateIdGeneratorService();
    }

    @Bean
    DatabaseIdGeneratorService produceDatabaseIdGeneratorService() {
        return new DatabaseIdGeneratorService();
    }

    @Bean
    DatabaseNameGeneratorService produceDatabaseNameGeneratorService() {
        return new DatabaseNameGeneratorService();
    }

    @Bean
    UsernameAndPasswordCredentialsGeneratorService produceCredentials(Random random) {
        return new UsernameAndPasswordCredentialsGeneratorService(random);
    }

    @Bean
    Random produceRandomGenerator() {
        return ThreadLocalRandom.current();
    }

    @Bean
    DatabaseTypeClassNameService produceDatabaseTypeClassName(OsgiBeanLocator locator) {
        return new OsgiDatabaseTypeClassNameService(locator);
    }

}
