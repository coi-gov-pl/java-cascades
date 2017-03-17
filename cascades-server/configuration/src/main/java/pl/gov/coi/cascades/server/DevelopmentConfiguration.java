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
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 17.03.17
 */
@Configuration
@Profile(Enviroment.DEVELOPMENT_NAME)
class DevelopmentConfiguration {
    private final Logger logger = LoggerFactory.getLogger(DevelopmentConfiguration.class);

    @EventListener(ContextRefreshedEvent.class)
    public void handle() {
        String asciiArt = tryToExecute(
            () -> FigletFont.convertOneLine(Enviroment.DEVELOPMENT_NAME),
            "20170317:144333"
        );
        String banner = "Running in environment\n\n{}\n";
        logger.info(
            banner,
            asciiArt
        );
    }
}
