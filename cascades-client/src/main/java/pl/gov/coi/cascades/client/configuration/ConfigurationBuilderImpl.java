package pl.gov.coi.cascades.client.configuration;

import pl.gov.coi.cascades.contract.configuration.Configuration;
import pl.gov.coi.cascades.contract.configuration.ConfigurationBuilder;
import pl.gov.coi.cascades.contract.configuration.Driver;
import pl.gov.coi.cascades.contract.configuration.Migration;
import pl.gov.coi.cascades.contract.configuration.Server;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import static pl.wavesoftware.eid.utils.EidPreconditions.checkNotNull;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 29.03.17
 */
final class ConfigurationBuilderImpl implements ConfigurationBuilder {

    private Server server;
    private Migration migration;
    private Driver driver;
    private String instanceName;
    private boolean tryToReuse = true;
    private URI cascadesServer;
    private long timeoutInSeconds;

    ConfigurationBuilderImpl() {
        // do nothing
    }

    @Override
    public ConfigurationBuilder reconfigure(Server server) {
        this.server = server;
        return this;
    }

    @Override
    public ConfigurationBuilder runMigration(Migration migration) {
        this.migration = migration;
        return this;
    }

    @Override
    public ConfigurationBuilder driver(Driver driver) {
        this.driver = driver;
        return this;
    }

    @Override
    public ConfigurationBuilder instanceName(String name) {
        this.instanceName = name;
        return this;
    }

    @Override
    public ConfigurationBuilder tryToReuse(boolean tryToReuse) {
        this.tryToReuse = tryToReuse;
        return this;
    }

    @Override
    public ConfigurationBuilder cascadesServer(URI cascadesServer) {
        this.cascadesServer = cascadesServer;
        return this;
    }

    @Override
    public ConfigurationBuilder operationsTimeout(long timeout, TimeUnit timeUnit) {
        this.timeoutInSeconds = timeUnit.toSeconds(timeout);
        return this;
    }

    @Override
    public Configuration build() {
        return new Configuration(
            checkNotNull(driver, "20170329:145416"),
            tryToReuse,
            timeoutInSeconds,
            server,
            migration,
            instanceName,
            cascadesServer
        );
    }
}
