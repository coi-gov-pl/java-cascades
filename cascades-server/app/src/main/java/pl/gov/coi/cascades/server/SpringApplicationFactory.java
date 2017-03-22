package pl.gov.coi.cascades.server;

import org.springframework.boot.SpringApplication;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 17.03.17
 */
class SpringApplicationFactory {
    SpringApplication create(Class<?> mainClass) {
        SpringApplication application = new SpringApplication(mainClass);
        application.addListeners(new ApplicationStartListener());
        return application;
    }
}
