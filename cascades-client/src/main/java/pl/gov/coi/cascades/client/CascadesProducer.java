package pl.gov.coi.cascades.client;

import pl.gov.coi.cascades.contract.Cascades;
import pl.gov.coi.cascades.contract.configuration.Configuration;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 29.03.17
 */
public interface CascadesProducer {
    /**
     * Creates cascades instance basing on configuration
     * @param configuration a configuration
     * @return a cascades instance
     */
    Cascades create(Configuration configuration);
}
