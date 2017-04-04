package pl.gov.coi.cascades.server;

import com.github.lalyos.jfiglet.FigletFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import static pl.wavesoftware.eid.utils.EidPreconditions.tryToExecute;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 04.04.17.
 */
@Configuration
@Profile(Environment.STUB_NAME)
class StubConfiguration {

    private final Logger logger = LoggerFactory.getLogger(StubConfiguration.class);

    @EventListener(ContextRefreshedEvent.class)
    public void handle() {
        String asciiArt = tryToExecute(
            () -> FigletFont.convertOneLine(Environment.STUB_NAME),
            "20170404:150225"
        );
        String banner = "Running in environment\n\n{}\n";
        logger.info(
            banner,
            asciiArt
        );
    }

}
