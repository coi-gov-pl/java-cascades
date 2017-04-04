package pl.gov.coi.cascades.client;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 29.03.17
 */
public interface CascadesClient {
    /**
     * Creates a cascades factory
     * @return a cascades factory
     */
    CascadesProducer factory();
}
