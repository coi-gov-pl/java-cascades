package pl.gov.coi.cascades.server;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.osgi.framework.BundleContext;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.04.17.
 */
public class SystemBundleActivatorTest {

    @Mock
    private BundleContext bundleContext;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testStart() throws Exception {
        // given
        SystemBundleActivator systemBundleActivator = new SystemBundleActivator();

        // when
        systemBundleActivator.start(bundleContext);
    }

    @Test
    public void testStop() throws Exception {
        // given
        SystemBundleActivator systemBundleActivator = new SystemBundleActivator();

        // when
        systemBundleActivator.stop(bundleContext);
    }

}
