package pl.gov.coi.cascades.server.persistance.stub;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseInstanceGateway;
import pl.wavesoftware.eid.exceptions.Eid;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.02.17.
 */
final class DatabaseInstanceGatewayStub implements DatabaseInstanceGateway {

    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(DatabaseInstanceGatewayStub.class);
    private Logger logger;
    private static final String DEFAULT_SERVER_ID = "868bb6ti";
    private static final NetworkBind NETWORK_BIND_STUB = NetworkBindStub.builder()
        .host("db01.lab.internal")
        .port(5432)
        .build();

    DatabaseInstanceGatewayStub() {
        this(DEFAULT_LOGGER);
    }

    @VisibleForTesting
    DatabaseInstanceGatewayStub(Logger logger) {
        this.logger = logger;
    }

    @Override
    public DatabaseInstance launchDatabase(DatabaseInstance databaseInstance) {
        DatabaseInstance databaseInstanceCopy = databaseInstance.setNetworkBind(NETWORK_BIND_STUB);
        if (logger.isInfoEnabled()) {
            logger.info(new Eid("20170419:001122").makeLogMessage(
                "Database has been launched. %s",
                databaseInstanceCopy.toString()
            ));
        }

        return databaseInstanceCopy;
    }

    @Override
    public void deleteDatabase(DatabaseInstance databaseInstance) {
        if (logger.isInfoEnabled()) {
            logger.info(new Eid("20170419:001226").makeLogMessage(
                "Database has been deleted. %s",
                databaseInstance.toString()
            ));
        }
    }

    @Override
    public String getRemoteServerId() {
        return DEFAULT_SERVER_ID;
    }

}
