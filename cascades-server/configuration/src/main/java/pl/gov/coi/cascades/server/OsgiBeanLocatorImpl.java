package pl.gov.coi.cascades.server;

import com.google.common.collect.Sets;
import org.osgi.framework.Bundle;
import org.osgi.framework.launch.Framework;
import org.osgi.util.tracker.ServiceTracker;
import org.springframework.beans.factory.DisposableBean;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static pl.wavesoftware.eid.utils.EidPreconditions.checkState;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 18.04.17.
 */
final class OsgiBeanLocatorImpl implements OsgiBeanLocator, DisposableBean {
    private final Map<Class, ServiceTracker> serviceTrackerMap = new HashMap<>();
    private final Framework framework;

    OsgiBeanLocatorImpl(Framework framework) {
        this.framework = framework;
    }

    @Override
    public <T> Iterable<T> getBeans(Class<T> cls) {
        ServiceTracker<T, T> serviceTracker = getServiceTracker(cls);
        @SuppressWarnings("unchecked")
        final T[] type = (T[]) Array.newInstance(cls, 0);
        T[] services = serviceTracker.getServices(type);
        return Sets.newHashSet(services);
    }

    @Override
    public <T> Optional<T> getBean(Class<T> cls) {
        ServiceTracker<T, T> serviceTracker = getServiceTracker(cls);
        @Nullable
        T service = serviceTracker.getService();
        return Optional.ofNullable(service);
    }

    @SuppressWarnings("unchecked")
    private synchronized <T> ServiceTracker<T, T> getServiceTracker(Class<T> cls) {
        if (!serviceTrackerMap.containsKey(cls)) {
            ServiceTracker<T, T> serviceTracker = createServiceTracker(cls);
            serviceTracker.open();
            serviceTrackerMap.put(cls, serviceTracker);
        }
        return serviceTrackerMap.get(cls);
    }

    private <T> ServiceTracker<T, T> createServiceTracker(Class<T> cls) {
        checkState(framework.getState() == Bundle.ACTIVE, "20170411:174945");
        return new ServiceTracker<>(
            framework.getBundleContext(),
            cls,
            null
        );
    }

    @Override
    public void destroy() throws Exception {
        serviceTrackerMap.values()
            .forEach(ServiceTracker::close);
    }
}
