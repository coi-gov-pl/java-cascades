package pl.gov.coi.cascades.server.persistance.stub;

import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseInstanceGateway;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.02.17.
 */
final class DatabaseInstanceGatewayStub implements DatabaseInstanceGateway {

    @Override
    public DatabaseInstance launchDatabase(DatabaseInstance databaseInstance) {
        return null;
    }
}
