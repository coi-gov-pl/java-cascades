package pl.gov.coi.cascades.server.persistance.stub;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseOperations;
import pl.wavesoftware.eid.exceptions.Eid;

final class DatabaseOperationsStub implements DatabaseOperations {

    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(DatabaseOperationsStub.class);
    private static final int PORT = 4312;
    private static final String EXAMPLE_HOST_COM = "example@host.com";
    private Logger logger;

    DatabaseOperationsStub() {
        this(DEFAULT_LOGGER);
    }

    @VisibleForTesting
    DatabaseOperationsStub(Logger logger) {
        this.logger = logger;
    }

    @Override
    public DatabaseInstance createDatabase(DatabaseInstance databaseInstance) {
        NetworkBindStub networkBindStub = new NetworkBindStub(PORT, EXAMPLE_HOST_COM);
        DatabaseInstance databaseInstanceNetworkBind = databaseInstance.setNetworkBind(networkBindStub);

        if (logger.isInfoEnabled()) {
            logger.info(new Eid("20180628:181922").makeLogMessage(
                "Database has been created. %s",
                networkBindStub.toString() + databaseInstance.toString()
            ));
        }
        return databaseInstanceNetworkBind;
    }

    @Override
    public void deleteDatabase(DatabaseInstance databaseInstance) {
        if (logger.isInfoEnabled()) {
            logger.info(new Eid("20180628:182022").makeLogMessage(
                "Database has been deleted. %s",
                databaseInstance.toString()
            ));
        }
    }
}
