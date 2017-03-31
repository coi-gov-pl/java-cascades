package pl.gov.coi.cascades.client.service;

import pl.gov.coi.cascades.contract.configuration.Configuration;
import pl.gov.coi.cascades.contract.service.CascadesDeleteService;
import pl.gov.coi.cascades.contract.service.CascadesLaunchService;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 30.03.17
 */
public interface ServiceFactory {
    /**
     * Create a launch service
     * @param configuration a configuration
     * @return a service
     */
    CascadesLaunchService createLaunchService(Configuration configuration);

    /**
     * Create a delete service
     * @param configuration a configuration
     * @return a service
     */
    CascadesDeleteService createDeleteService(Configuration configuration);
}
