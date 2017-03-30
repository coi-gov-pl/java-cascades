package pl.gov.coi.cascades.client.service;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 30.03.17
 */
public interface ServiceExecutionContext<T> {
    String getOperationName();
    T getRequest();
}
