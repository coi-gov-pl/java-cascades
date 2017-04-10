package pl.gov.coi.cascades.server;

import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.osgi.framework.Bundle;
import org.osgi.framework.launch.Framework;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.wavesoftware.eid.utils.EidPreconditions;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static pl.wavesoftware.eid.utils.EidPreconditions.tryToExecute;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 15.03.17
 */
@Component
@Configuration
@RequiredArgsConstructor
class OsgiContainer implements SmartLifecycle {

    private static final long STOP_TIMEOUT = 1000L * 30; // 30sec
    private final Framework framework;
    private final Logger logger = LoggerFactory.getLogger(OsgiContainer.class);
    private Status status = Status.STOPPED;

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback) {
        changeStatus(Status.STOPPING);
        handleStop();
        changeStatus(Status.STOPPED);
        callback.run();
    }

    @Override
    public void start() {
        changeStatus(Status.RUNNING);
        startContainer();
        changeStatus(Status.RUN);
    }

    @Override
    public void stop() {
        // do nothing
    }

    @Override
    public boolean isRunning() {
        return status == Status.RUN;
    }

    @Override
    public int getPhase() {
        return status.ordinal();
    }

    @EventListener(ContextRefreshedEvent.class)
    private synchronized void ensureContainerIsStated() {
        if (framework.getState() != Bundle.ACTIVE) {
            startContainer();
        }
    }

    private void startContainer() {
        logger.info("Starting OSGi Container - {}", getFrameworkName());
        tryToExecute((EidPreconditions.UnsafeProcedure) framework::start, "20170315:134841");

    }

    private void handleStop() {
        logger.info("Stopping OSGi Container - {}", getFrameworkName());
        tryToExecute((EidPreconditions.UnsafeProcedure) framework::stop, "20170315:135147");
        tryToExecute(() -> framework.waitForStop(STOP_TIMEOUT), "20170315:160521");
    }

    private String getFrameworkName() {
        return framework.getClass().getSimpleName();
    }

    @Bean
    OsgiBeanLocator provideOsgiBeanLocator() {
        ensureContainerIsStated();
        return new OsgiBeanLocatorImpl(framework);
    }

    private static final class OsgiBeanLocatorImpl implements OsgiBeanLocator, DisposableBean {
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
            return new ServiceTracker<>(
                framework.getBundleContext(),
                cls,
                null
            );
        }

        @Override
        public void destroy() throws Exception {
            for (ServiceTracker serviceTracker : serviceTrackerMap.values()) {
                serviceTracker.close();
            }
        }
    }

    private void changeStatus(Status status) {
        logger.info("Changing status from {} to {}", this.status, status);
        this.status = status;
    }

    private enum Status {
        RUNNING,
        RUN,
        STOPPING,
        STOPPED
    }

}
