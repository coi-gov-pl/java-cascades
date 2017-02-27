package pl.gov.coi.cascades.server.domain;

public class LaunchNewDatabaseInstanceUseCaseImpl implements LaunchNewDatabaseInstanceUseCase {

    /**
     * This method takes a pair of request and response objects. That ensures decoupling of presentation from domain.
     *
     * @param request Given request of launching new database instance.
     * @param response Given response of launching new darabase instance.
     */
    @Override
    public void execute(LaunchNewDatabaseInstanceRequest request, LaunchNewDatabaseInstanceResponse response) {
        throw new UnsupportedOperationException();
    }
}
