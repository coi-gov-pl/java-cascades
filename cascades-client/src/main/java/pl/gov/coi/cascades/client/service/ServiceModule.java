package pl.gov.coi.cascades.client.service;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.mashape.unirest.http.ObjectMapper;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 30.03.17
 */
public class ServiceModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(ServiceFactory.class)
            .to(ServiceFactoryImpl.class);
        binder.bind(ObjectMapper.class)
            .to(CascadesObjectMapper.class);
    }
}
