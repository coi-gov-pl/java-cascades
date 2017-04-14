package pl.gov.coi.cascades.server;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import static pl.wavesoftware.eid.utils.EidPreconditions.checkElementIndex;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 15.03.17
 */
@Configuration
@RequiredArgsConstructor
public class OsgiAutoConfiguration {

    private static final String FELIX_SYSTEMBUNDLE_ACTIVATORS_PROP = "felix.systembundle.activators";
    private static final String FELIX_PACKAGE_SUBSTRING = "felix";
    private static final int FIRST_ELEMENT_INDEX = 0;
    private final List<BundleActivator> bundleActivators;

    @Bean
    @ConditionalOnMissingBean
    OsgiFrameworkConfigurator produceAutoConfiguredOsgiFrameworkConfigurator() {
        return config -> {
            // do nothing
        };
    }

    @Bean
    FrameworkFactory produceFrameworkFactory() {
        ServiceLoader<FrameworkFactory> loader = ServiceLoader.load(FrameworkFactory.class);
        List<FrameworkFactory> factories = Lists.newArrayList(loader);
        checkElementIndex(FIRST_ELEMENT_INDEX, factories.size(), "20170315:124206");
        return factories.get(FIRST_ELEMENT_INDEX);
    }

    @Bean
    public Framework produceFramework(FrameworkFactory factory,
                               OsgiFrameworkConfigurator configurator) {
        Map<String, String> config = createConfigurationMap(factory);
        configurator.configure(config);
        return factory.newFramework(config);
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> createConfigurationMap(FrameworkFactory factory) {
        Map<String, String> config = new HashMap<>();
        if (factory.getClass().getPackage().getName().contains(FELIX_PACKAGE_SUBSTRING)) {
            config = createFelixConfigurationMap();
        }
        return config;
    }

    private Map createFelixConfigurationMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(
            FELIX_SYSTEMBUNDLE_ACTIVATORS_PROP,
            bundleActivators
        );
        return map;
    }

}
