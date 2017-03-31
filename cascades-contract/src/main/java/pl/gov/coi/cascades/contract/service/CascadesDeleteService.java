package pl.gov.coi.cascades.contract.service;

import pl.gov.coi.cascades.contract.domain.DatabaseId;

import java.util.concurrent.Future;

/**
 * This service performs main function of Cascades project - it deletes a remote database instances.
 */
public interface CascadesDeleteService {

    /**
     * Method deletes remote database by given {@link DatabaseId}
     *
     * @param databaseId Given id of database.
     * @return a future of void, just to know if operation succeeded
     */
    Future<WithViolations<Void>> deleteDatabase(DatabaseId databaseId);

}
