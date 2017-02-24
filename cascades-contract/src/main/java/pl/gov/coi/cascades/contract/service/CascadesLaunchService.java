package pl.gov.coi.cascades.contract.service;

import java.util.concurrent.Future;

/**
 * This service performs main function of Cascades project - it launches a remote database instances.
 */
public interface CascadesLaunchService {

    /**
     * Method gives future of specification of remote database for given request.
     *
     * @param request Given database creation request.
     * @return a future of remote database specification
     */
    Future<RemoteDatabaseSpec> launchDatabase(RemoteDatabaseRequest request);

}
