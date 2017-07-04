package pl.gov.coi.cascades.server.domain.loadtemplate;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.05.17.
 *
 * Use case for loading templates.
 */
@FunctionalInterface
public interface UseCase {

    /**
     * This method takes a pair of request and response objects. That ensures decoupling of presentation from domain.
     *
     * @param request Given request of loading templates.
     * @param response Given response of loading templates.
     */
    void execute(Request request, Response response);

}
