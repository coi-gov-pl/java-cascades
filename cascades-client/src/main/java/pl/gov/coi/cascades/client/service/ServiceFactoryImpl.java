package pl.gov.coi.cascades.client.service;

import com.mashape.unirest.http.ObjectMapper;
import pl.gov.coi.cascades.contract.configuration.Configuration;
import pl.gov.coi.cascades.contract.service.CascadesDeleteService;
import pl.gov.coi.cascades.contract.service.CascadesLaunchService;

import javax.inject.Inject;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 30.03.17
 */
class ServiceFactoryImpl implements ServiceFactory {
    private final ObjectMapper objectMapper;

    @Inject
    ServiceFactoryImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public CascadesLaunchService createLaunchService(Configuration configuration) {
        return new RestCascadesLaunchService(
            configuration.getCascadesServerUri(),
            objectMapper
        );
    }

    @Override
    public CascadesDeleteService createDeleteService(Configuration configuration) {
        // TODO: write an implementation
        throw new UnsupportedOperationException("Not yet implemented!");
    }
}
