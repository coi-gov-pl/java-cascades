package pl.gov.coi.cascades.server;

import com.github.lalyos.jfiglet.FigletFont;
import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import pl.wavesoftware.eid.exceptions.Eid;

import static pl.wavesoftware.eid.utils.EidPreconditions.tryToExecute;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 17.03.17
 */
@Configuration
@Profile(Environment.DEVELOPMENT_NAME)
class DevelopmentConfiguration {

    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(DevelopmentConfiguration.class);
    private final Logger logger;

    DevelopmentConfiguration() {
        this(DEFAULT_LOGGER);
    }

    @VisibleForTesting
    DevelopmentConfiguration(Logger logger) {
        this.logger = logger;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void handle() {
        String asciiArt = tryToExecute(
            () -> FigletFont.convertOneLine(Environment.DEVELOPMENT_NAME),
            "20170317:144333"
        );
        String banner = "Running in environment\n\n%s\n";
        if (logger.isInfoEnabled()) {
            logger.info(new Eid("20170418:134033").makeLogMessage(
                banner,
                asciiArt
            ));
        }
    }
}
