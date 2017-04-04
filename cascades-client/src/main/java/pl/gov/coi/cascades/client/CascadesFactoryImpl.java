package pl.gov.coi.cascades.client;

import pl.gov.coi.cascades.client.presentation.CascadesOperationsLogger;
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
class CascadesFactoryImpl implements CascadesFactory {

    private final CascadesOperationsLogger operationsLogger;

    @Inject
    CascadesFactoryImpl(CascadesOperationsLogger operationsLogger) {
        this.operationsLogger = operationsLogger;
    }

    @Override
    public Cascades create(Configuration configuration,
                           CascadesLaunchService cascadesLaunchService,
                           CascadesDeleteService cascadesDeleteService) {
        return new CascadesImpl(
            configuration,
            cascadesLaunchService,
            cascadesDeleteService,
            operationsLogger
        );
    }
}
