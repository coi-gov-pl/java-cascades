package pl.gov.coi.cascades.plugin.postgresql;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import pl.gov.coi.cascades.contract.domain.DatabaseType;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 26.06.18
 */
public class PostgreSQLActivator implements BundleActivator {

    private final Injector injector = Guice.createInjector(new PostgresModule());
    private ServiceRegistration<DatabaseType> databaseTypeRegistration;

    @Override
    public void start(BundleContext context) {
        databaseTypeRegistration = context.registerService(
            DatabaseType.class,
            injector.getInstance(DatabaseType.class),
            null
        );
    }

    @Override
    public void stop(BundleContext context) {
        databaseTypeRegistration.unregister();
    }
}
