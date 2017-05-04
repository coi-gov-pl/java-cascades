package pl.gov.coi.cascades.server.domain.launchdatabase;

/**
 * Use case for launching new database instance.
 */
@FunctionalInterface
public interface UseCase {

    /**
     * This method takes a pair of request and response objects. That ensures decoupling of presentation from domain.
     *
     * @param request Given request of launching new database instance.
     * @param response Given response of launching new darabase instance.
     */
    void execute(Request request, Response response);

}
