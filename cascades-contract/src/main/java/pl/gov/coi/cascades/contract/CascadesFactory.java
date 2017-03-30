package pl.gov.coi.cascades.contract;

import pl.gov.coi.cascades.contract.configuration.Configuration;
import pl.gov.coi.cascades.contract.service.CascadesDeleteService;
import pl.gov.coi.cascades.contract.service.CascadesLaunchService;

/**
 * @author <a href="mailto:krzysztof.suszynski@gmail.com">Krzysztof Suszyński</a>
 * @since 21.02.17
 */
public interface CascadesFactory {

    /**
     * Method gives cascades for given configuration and service of cascades.
     *
     * @param configuration         Given configuration.
     * @param cascadesLaunchService Given launch service.
     * @param cascadesDeleteService Given delete service.
     * @return a complete cascades object
     */
    Cascades create(Configuration configuration,
                    CascadesLaunchService cascadesLaunchService,
                    CascadesDeleteService cascadesDeleteService);

}
