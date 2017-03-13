package pl.gov.coi.cascades.server.domain;

import pl.gov.coi.cascades.contract.domain.DatabaseId;

import java.util.Optional;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 10.03.17.
 */
public interface DatabaseIdGateway {

    Optional<DatabaseInstance> findInstance(DatabaseId databaseId);

}
