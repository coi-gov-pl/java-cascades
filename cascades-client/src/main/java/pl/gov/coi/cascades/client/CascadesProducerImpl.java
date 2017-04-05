package pl.gov.coi.cascades.client;

import pl.gov.coi.cascades.client.service.ServiceFactory;
import pl.gov.coi.cascades.contract.Cascades;
import pl.gov.coi.cascades.contract.CascadesFactory;
import pl.gov.coi.cascades.contract.configuration.Configuration;
import pl.gov.coi.cascades.contract.service.CascadesDeleteService;
import pl.gov.coi.cascades.contract.service.CascadesLaunchService;

import javax.inject.Inject;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 29.03.17
 */
class CascadesProducerImpl implements CascadesProducer {
    private final ServiceFactory serviceFactory;
    private final CascadesFactory cascadesFactory;

    @Inject
    CascadesProducerImpl(ServiceFactory serviceFactory,
                         CascadesFactory cascadesFactory) {
        this.serviceFactory = serviceFactory;
        this.cascadesFactory = cascadesFactory;
    }

    @Override
    public Cascades create(Configuration configuration) {
        CascadesLaunchService launchService = serviceFactory.createLaunchService(configuration);
        CascadesDeleteService deleteService = serviceFactory.createDeleteService(configuration);
        return cascadesFactory.create(
            configuration, launchService, deleteService
        );
    }
}
