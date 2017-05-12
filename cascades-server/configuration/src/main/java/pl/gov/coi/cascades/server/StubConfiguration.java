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
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 04.04.17.
 */
@Configuration
@Profile(ProfileType.STUB_NAME)
class StubConfiguration {

    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(StubConfiguration.class);
    private final Logger logger;

    StubConfiguration() {
        this(DEFAULT_LOGGER);
    }

    @VisibleForTesting
    StubConfiguration(Logger logger) {
        this.logger = logger;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void handle() {
        String asciiArt = tryToExecute(
            () -> FigletFont.convertOneLine(ProfileType.STUB_NAME),
            "20170404:150225"
        );
        String banner = "Running in environment\n\n%s\n";
        if (logger.isInfoEnabled()) {
            logger.info(new Eid("20170419:000235").makeLogMessage(
                banner,
                asciiArt
            ));
        }
    }

}
