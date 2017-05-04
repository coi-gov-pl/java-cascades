package pl.gov.coi.cascades.server;

import com.github.lalyos.jfiglet.FigletFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.AbstractEnvironment;
import pl.wavesoftware.eid.exceptions.Eid;

import static pl.wavesoftware.eid.utils.EidPreconditions.tryToExecute;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 17.03.17
 */
@Configuration
@Profile(Environment.PRODUCTION_NAME)
class ProductionConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ProductionConfiguration.class);

    @EventListener(ContextRefreshedEvent.class)
    public void handle() {
        String asciiArt = tryToExecute(
            () -> FigletFont.convertOneLine(Environment.PRODUCTION_NAME),
            "20170317:135228"
        );
        String banner = "Running in environment\n\n%s\n" +
            "Set development environment with command line arg " +
            "`--%s=%s` " +
            "or set environment variable with " +
            "`export %s=%s`\n";
        if (logger.isInfoEnabled()) {
            logger.info(new Eid("20170419:000022").makeLogMessage(
                banner,
                asciiArt,
                AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME,
                Environment.DEVELOPMENT_NAME,
                enviromentalize(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME),
                Environment.DEVELOPMENT_NAME
            ));
        }
    }

    private static String enviromentalize(String propertyName) {
        return propertyName.replace(".", "_");
    }
}
