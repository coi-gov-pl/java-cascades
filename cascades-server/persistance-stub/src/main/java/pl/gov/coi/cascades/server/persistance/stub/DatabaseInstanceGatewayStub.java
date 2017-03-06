package pl.gov.coi.cascades.server.persistance.stub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseInstanceGateway;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.02.17.
 */
final class DatabaseInstanceGatewayStub implements DatabaseInstanceGateway {

    private final Logger logger = LoggerFactory.getLogger(DatabaseInstanceGatewayStub.class);

    @Override
    public DatabaseInstance launchDatabase(DatabaseInstance databaseInstance) {
        logger.debug(
            "Database has been launched. "
            + "Database id: " + databaseInstance.getDatabaseId() + ", "
            + "Database name: " + databaseInstance.getDatabaseName() + ", "
            + "Database type: " + databaseInstance.getDatabaseType() + ", "
            + "Instance name: " + databaseInstance.getInstanceName() + ", "
            + "Template id: " + databaseInstance.getTemplateId() + ", "
            + "Status: " + databaseInstance.getStatus()
        );
        return databaseInstance;
    }
}
