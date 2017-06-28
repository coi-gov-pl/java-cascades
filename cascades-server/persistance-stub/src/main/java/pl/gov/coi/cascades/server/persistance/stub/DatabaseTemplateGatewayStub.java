package pl.gov.coi.cascades.server.persistance.stub;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gov.coi.cascades.server.domain.DatabaseTemplateGateway;
import pl.wavesoftware.eid.exceptions.Eid;

import java.nio.file.Path;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 28.06.17
 */
public class DatabaseTemplateGatewayStub implements DatabaseTemplateGateway {

    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(DatabaseTemplateGatewayStub.class);
    private Logger logger;

    DatabaseTemplateGatewayStub() {
        this(DEFAULT_LOGGER);
    }

    @VisibleForTesting
    DatabaseTemplateGatewayStub(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void loadTemplate(Path path) {
        if (logger.isInfoEnabled()) {
            logger.info(new Eid("20170628:135108")
                .makeLogMessage(
                    "Script, loaded in " + path + " has been saved."
                )
            );
        }
    }

}
