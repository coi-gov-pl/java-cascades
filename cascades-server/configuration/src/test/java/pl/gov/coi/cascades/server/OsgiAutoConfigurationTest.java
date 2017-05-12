package pl.gov.coi.cascades.server;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 28.04.17.
 */
public class OsgiAutoConfigurationTest {

    @Mock
    private BundleActivator bundleActivator;

    @Mock
    private FrameworkFactory frameworkFactory;

    @Mock
    private org.apache.felix.framework.FrameworkFactory felixFramework;

    @Mock
    private OsgiFrameworkConfigurator osgiFrameworkConfigurator;

    @Mock
    private Package aPackage;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testProduceAutoConfiguredOsgiFrameworkConfigurator() throws Exception {
        // given
        List<BundleActivator> bundleActivators = new ArrayList<>();
        bundleActivators.add(bundleActivator);
        OsgiAutoConfiguration osgiAutoConfiguration = new OsgiAutoConfiguration(bundleActivators);

        // when
        OsgiFrameworkConfigurator actual = osgiAutoConfiguration.produceAutoConfiguredOsgiFrameworkConfigurator();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isInstanceOf(OsgiFrameworkConfigurator.class);
    }

    @Test
    public void testProduceFrameworkFactory() throws Exception {
        // given
        List<BundleActivator> bundleActivators = new ArrayList<>();
        bundleActivators.add(bundleActivator);
        OsgiAutoConfiguration osgiAutoConfiguration = new OsgiAutoConfiguration(bundleActivators);

        // when
        FrameworkFactory actual = osgiAutoConfiguration.produceFrameworkFactory();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testProduceFramework() throws Exception {
        // given
        List<BundleActivator> bundleActivators = new ArrayList<>();
        bundleActivators.add(bundleActivator);
        OsgiAutoConfiguration osgiAutoConfiguration = new OsgiAutoConfiguration(bundleActivators);
        doNothing().when(osgiFrameworkConfigurator).configure(anyMapOf(String.class, String.class));

        // when
        Framework actual = osgiAutoConfiguration.produceFramework(
            felixFramework,
            osgiFrameworkConfigurator
        );

        // then
        verify(felixFramework, times(1)).newFramework(anyMapOf(String.class, String.class));
    }

}
