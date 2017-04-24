package pl.gov.coi.cascades.server.persistance.stub;

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

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInstanceGatewayStub.class);
    private static final NetworkBind NETWORK_BIND_STUB = NetworkBindStub.builder()
        .host("db01.lab.internal")
        .port(5432)
        .build();

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
}
