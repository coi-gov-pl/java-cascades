package pl.gov.coi.cascades.junit4;

import lombok.AccessLevel;
import lombok.Getter;
import org.junit.rules.ExternalResource;
import pl.gov.coi.cascades.client.CascadesClient;
import pl.gov.coi.cascades.client.CascadesProducer;
import pl.gov.coi.cascades.client.ClientModule;
import pl.gov.coi.cascades.client.configuration.Container;
import pl.gov.coi.cascades.client.presentation.CascadesOperationsLogger;
import pl.gov.coi.cascades.contract.Cascades;
import pl.gov.coi.cascades.contract.configuration.Configuration;
import pl.gov.coi.cascades.contract.service.RemoteDatabaseSpec;

import javax.annotation.Nullable;

import static pl.wavesoftware.eid.utils.EidPreconditions.checkNotNull;
import static pl.wavesoftware.eid.utils.EidPreconditions.checkState;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 29.03.17
 */
public final class CascadesRule extends ExternalResource {
    private final Configuration configuration;
    private final CascadesOperationsLogger operationsLogger;
    @Getter(AccessLevel.PRIVATE)
    private static boolean setup;
    @Nullable
    private Cascades cascades;

    CascadesRule(Configuration configuration,
                 CascadesOperationsLogger operationsLogger) {
        this.configuration = checkNotNull(configuration, "20170329:140155");
        this.operationsLogger = operationsLogger;
    }

    /**
     * Creates a builder that can be used to configure and build a JUnit rule in fluent way
     *
     * @return a builder
     */
    @SuppressWarnings("WeakerAccess")
    public static CascadesRuleBuilder builder() {
        return getContainer().getBean(CascadesRuleBuilder.class);
    }

    @Override
    protected void before() {
        // 1. setup
        cascades = setupCascades(configuration);

        // 2. create db
        checkState(!cascades.isCreated(), "20170329:133140");
        cascades.createDatabase();
        logDatabaseCreated(cascades.getSpec());

        // 3. migrate db
        migrateIfAny(cascades);

        // 4. reconfigure app server
        reconfigureAppServerIfAny(cascades);
    }

    @Override
    protected void after() {
        if (cascades != null && configuration != null) {
            tearDown(cascades);
        }
    }

    private static Container getContainer() {
        if (!isSetup()) {
            setupContainer();
        }
        return Container.INSTANCE;
    }

    private static void setupContainer() {
        Container.INSTANCE
            .register(new ClientModule())
            .register(new JUnit4Module());

        setup = true;
    }

    private Cascades setupCascades(Configuration configuration) {
        CascadesClient client = getContainer().getBean(CascadesClient.class);
        CascadesProducer factory = client.factory();
        return factory.create(configuration);
    }

    private void tearDown(Cascades cascades) {
        // remove db
        cascades.removeDatabase();
        logDatabaseRemoved(cascades.getSpec());

        // restore app server
        restoreAppServerIfAny(cascades);
    }

    private void reconfigureAppServerIfAny(Cascades cascades) {
        // TODO: write implementation
    }

    private void restoreAppServerIfAny(Cascades cascades) {
        // TODO: write implementation
    }

    private void migrateIfAny(Cascades cascades) {
        // TODO: write implementation
    }

    private void logDatabaseCreated(RemoteDatabaseSpec spec) {
        operationsLogger.logDatabaseCreated(spec);
    }

    private void logDatabaseRemoved(RemoteDatabaseSpec spec) {
        operationsLogger.logDatabaseRemoved(spec);
    }

}
