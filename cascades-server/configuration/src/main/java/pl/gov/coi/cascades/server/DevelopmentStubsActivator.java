package pl.gov.coi.cascades.server;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.server.persistance.stub.DatabaseTypeStub;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 16.03.17
 */
@Component
@Profile(Environment.DEVELOPMENT_NAME)
public class DevelopmentStubsActivator implements BundleActivator {
    private ServiceRegistration<DatabaseType> databaseTypeRegistration;
    private final Logger logger = LoggerFactory.getLogger(DevelopmentStubsActivator.class);

    @Override
    public void start(BundleContext context) throws Exception {
        logger.info("Staring OSGi bundle - {}", name());
        databaseTypeRegistration = context.registerService(
            DatabaseType.class,
            new DatabaseTypeStub(),
            null
        );
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        logger.info("Stopping OSGi bundle - {}", name());
        databaseTypeRegistration.unregister();
    }

    private String name() {
        return this.getClass().getSimpleName();
    }
}
