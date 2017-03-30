package pl.gov.coi.cascades.client;

import com.google.inject.Binder;
import com.google.inject.Module;
import pl.gov.coi.cascades.client.configuration.ConfigurationModule;
import pl.gov.coi.cascades.client.presentation.PresentationModule;
import pl.gov.coi.cascades.client.service.ServiceModule;
import pl.gov.coi.cascades.contract.CascadesFactory;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 29.03.17
 */
public class ClientModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.install(new ConfigurationModule());
        binder.install(new PresentationModule());
        binder.install(new ServiceModule());

        binder.bind(CascadesProducer.class)
            .to(CascadesProducerImpl.class);
        binder.bind(CascadesFactory.class)
            .to(CascadesFactoryImpl.class);
    }
}
