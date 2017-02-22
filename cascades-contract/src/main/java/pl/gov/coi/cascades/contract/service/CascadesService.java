package pl.gov.coi.cascades.contract.service;

import pl.gov.coi.cascades.contract.domain.DatabaseId;

import java.util.concurrent.Future;

/**
 * This service performs main functions of Cascades project - it launches and deletes
 * a remote database instances
 */
public interface CascadesService {

    /**
     * Method gives future of specification of remote database for given request.
     *
     * @param request Given database creation request.
     * @return a future of remote database specification
     */
    Future<RemoteDatabaseSpec> launchDatabase(RemoteDatabaseRequest request);

    /**
     * Method deletes remote database by given {@link DatabaseId}
     *
     * @param databaseId Given id of database.
     * @return a future of void, just to know if operation succeeded
     */
    Future<Void> deleteDatabase(DatabaseId databaseId);

}
