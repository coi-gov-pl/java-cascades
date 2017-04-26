package pl.gov.coi.cascades.server;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 17.03.17
 */
class ApplicationStartListener implements
    ApplicationListener<ApplicationEnvironmentPreparedEvent>, Ordered {

    private static final int PRIORITY = 5;

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment env = event.getEnvironment();
        env.setDefaultProfiles(
            Environment.PRODUCTION_NAME,
            ProfileType.HIBERNATE_NAME
        );
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + PRIORITY;
    }

}
