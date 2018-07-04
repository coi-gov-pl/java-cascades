package pl.gov.coi.cascades.server.persistance.stub;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseUserGateway;
import pl.wavesoftware.eid.exceptions.Eid;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
public class DatabaseUserGatewayStub implements DatabaseUserGateway {

    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(DatabaseTemplateGatewayStub.class);
    private Logger logger;

    DatabaseUserGatewayStub() {
        this(DEFAULT_LOGGER);
    }

    @VisibleForTesting
    public DatabaseUserGatewayStub(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void createUserPostgres(DatabaseInstance databaseInstance) {
        if (logger.isInfoEnabled()) {
            logger.info(new Eid("20180704:094608")
                .makeLogMessage(
                    "Given user has been successfully created."
                )
            );
        }
    }

    @Override
    public void deleteUser(DatabaseInstance databaseInstance) {
        if (logger.isInfoEnabled()) {
            logger.info(new Eid("20180704:094708")
                .makeLogMessage(
                    "Given user has been successfully deleted."
                )
            );
        }
    }
}
