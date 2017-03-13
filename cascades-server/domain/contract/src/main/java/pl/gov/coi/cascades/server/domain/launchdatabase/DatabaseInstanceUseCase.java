package pl.gov.coi.cascades.server.domain.launchdatabase;

/**
 * Use case for launching new database instance.
 */
public interface DatabaseInstanceUseCase {

    /**
     * This method takes a pair of request and response objects. That ensures decoupling of presentation from domain.
     *
     * @param request Given request of launching new database instance.
     * @param response Given response of launching new darabase instance.
     */
    void execute(DatabaseInstanceRequest request, DatabaseInstanceResponse response);

}
