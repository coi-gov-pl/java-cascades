package pl.gov.coi.cascades.server.persistance.hibernate.development;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.gov.coi.cascades.server.Environment;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.03.17.
 */
@Configuration
@Profile(Environment.DEVELOPMENT_NAME)
class DevelopmentDataConfiguration {

    @Bean
    JpaDevelopmentData provideDevelopmentData() {
        return new JpaDevelopmentData();
    }
}
