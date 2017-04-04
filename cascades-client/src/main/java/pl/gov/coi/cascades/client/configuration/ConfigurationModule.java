package pl.gov.coi.cascades.client.configuration;

import com.google.inject.Binder;
import com.google.inject.Module;
import pl.gov.coi.cascades.contract.configuration.ConfigurationBuilder;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 29.03.17
 */
public class ConfigurationModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(CascadesServerConfiguration.class)
            .to(CascadesServerConfigurationImpl.class);
        binder.bind(ConfigurationBuilder.class)
            .to(ConfigurationBuilderImpl.class);
    }
}
