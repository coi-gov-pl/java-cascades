package pl.gov.coi.cascades.server;

import org.osgi.framework.Bundle;
import org.osgi.framework.launch.Framework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.wavesoftware.eid.utils.EidPreconditions;

import javax.inject.Inject;

import static pl.wavesoftware.eid.utils.EidPreconditions.checkState;
import static pl.wavesoftware.eid.utils.EidPreconditions.tryToExecute;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 15.03.17
 */
@Component
@Configuration
class OsgiContainer implements SmartLifecycle {

    private static final long STOP_TIMEOUT = 1000L * 30; // 30sec
    private final Framework framework;
    private static final Logger logger = LoggerFactory.getLogger(OsgiContainer.class);
    private Status status = Status.STOPPED;

    @Inject
    OsgiContainer(Framework framework) {
        this.framework = framework;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback) {
        handleStop();
        callback.run();
    }

    @Override
    public void start() {
        // do nothing
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
        changeStatus(Status.RUNNING);
        logger.info("Starting OSGi Container - {}", getFrameworkName());
        tryToExecute((EidPreconditions.UnsafeProcedure) framework::start, "20170315:134841");
        changeStatus(Status.RUN);
    }

    private void handleStop() {
        changeStatus(Status.STOPPING);
        logger.info("Stopping OSGi Container - {}", getFrameworkName());
        tryToExecute((EidPreconditions.UnsafeProcedure) framework::stop, "20170315:135147");
        tryToExecute(() -> framework.waitForStop(STOP_TIMEOUT), "20170315:160521");
        changeStatus(Status.STOPPED);
    }

    private String getFrameworkName() {
        return framework.getClass().getSimpleName();
    }

    @Bean
    OsgiBeanLocator provideOsgiBeanLocator() {
        return new OsgiBeanLocatorImpl(framework);
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
