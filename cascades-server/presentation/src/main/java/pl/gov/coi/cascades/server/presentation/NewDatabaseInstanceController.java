package pl.gov.coi.cascades.server.presentation;

import pl.gov.coi.cascades.contract.service.CascadesLaunchService;
import pl.gov.coi.cascades.contract.service.RemoteDatabaseRequest;
import pl.gov.coi.cascades.contract.service.RemoteDatabaseSpec;

import java.util.concurrent.Future;

public class NewDatabaseInstanceController implements CascadesLaunchService {

    /**
     * Method gives future of specification of remote database for given request.
     *
     * @param request Given database creation request.
     * @return a future of remote database specification
     */
    @Override
    public Future<RemoteDatabaseSpec> launchDatabase(RemoteDatabaseRequest request) {
        return null;
    }
}
