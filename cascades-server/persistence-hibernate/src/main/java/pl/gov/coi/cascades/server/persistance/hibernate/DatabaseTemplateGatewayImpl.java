package pl.gov.coi.cascades.server.persistance.hibernate;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gov.coi.cascades.server.domain.DatabaseTemplateGateway;
import pl.wavesoftware.eid.exceptions.Eid;

import java.nio.file.Path;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 28.06.17
 */
@AllArgsConstructor
public class DatabaseTemplateGatewayImpl implements DatabaseTemplateGateway {

    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(DatabaseTemplateGateway.class);
    private Logger logger;

    DatabaseTemplateGatewayImpl() {
        this(DEFAULT_LOGGER);
    }

    @Override
    public void loadTemplate(Path path) {
        if (logger.isInfoEnabled()) {
            logger.info(new Eid("20170628:133136")
                .makeLogMessage(
                    "Script, loaded in " + path + " has been saved."
                )
            );
        }
    }

}
