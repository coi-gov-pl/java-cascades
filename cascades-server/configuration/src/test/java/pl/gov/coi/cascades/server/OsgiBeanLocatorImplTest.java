package pl.gov.coi.cascades.server;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.launch.Framework;
import org.osgi.util.tracker.ServiceTracker;
import pl.gov.coi.cascades.contract.domain.DatabaseType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 28.04.17.
 */
public class OsgiBeanLocatorImplTest {

    @Mock
    private Framework framework;

    @Mock
    private BundleContext bundleContext;

    @Mock
    private ServiceTracker serviceTracker;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testGetBeans() throws Exception {
        // given
        when(framework.getState()).thenReturn(Bundle.ACTIVE);
        when(framework.getBundleContext()).thenReturn(bundleContext);
        OsgiBeanLocatorImpl osgiBeanLocator = new OsgiBeanLocatorImpl(framework);

        // when
        Iterable<DatabaseType> actual = osgiBeanLocator.getBeans(DatabaseType.class);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isInstanceOf(HashSet.class);
    }

    @Test
    public void testGetBeansWhenExceptionOccurred() throws Exception {
        // given
        when(framework.getState()).thenReturn(Bundle.ACTIVE);
        when(framework.getBundleContext()).thenReturn(bundleContext);
        OsgiBeanLocatorImpl osgiBeanLocator = new OsgiBeanLocatorImpl(framework);

        // then
        expectedException.expectMessage(contains("20170411:174945"));

        // when
        osgiBeanLocator.getBeans(DatabaseType.class);
    }

    @Test
    public void testGetBean() throws Exception {
        // given
        when(framework.getState()).thenReturn(Bundle.ACTIVE);
        when(framework.getBundleContext()).thenReturn(bundleContext);
        OsgiBeanLocatorImpl osgiBeanLocator = new OsgiBeanLocatorImpl(framework);

        // when
        Optional<DatabaseType> actual = osgiBeanLocator.getBean(DatabaseType.class);

        // then
        assertThat(actual).isEqualTo(Optional.empty());
    }

    @Test
    public void testDestroy() throws Exception {
        // given
        Map<Class, ServiceTracker> serviceTrackerMap = new HashMap<>();
        serviceTrackerMap.put(DatabaseType.class, serviceTracker);
        when(framework.getState()).thenReturn(Bundle.ACTIVE);
        when(framework.getBundleContext()).thenReturn(bundleContext);
        OsgiBeanLocatorImpl osgiBeanLocator = new OsgiBeanLocatorImpl(
            framework,
            serviceTrackerMap
        );

        // when
        osgiBeanLocator.destroy();

        // then
        for (ServiceTracker serviceTracker : serviceTrackerMap.values()) {
            verify(serviceTracker, times(1)).close();
        }
    }

}
