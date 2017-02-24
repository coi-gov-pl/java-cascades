package pl.gov.coi.cascades.contract.configuration;

import com.google.common.base.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.gov.coi.cascades.contract.domain.NetworkBind;

import javax.annotation.Nullable;

/**
 * Configuration class for all Cascades operations
 */
public class Configuration {

    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 7890;
    private static final NetworkBind DEFAULT_NETWORK_BIND =
        new SimpleNetworkBind(DEFAULT_HOST, DEFAULT_PORT);

    @Getter
    private final Server server;
    @Getter
    private final Migration migration;
    @Getter
    private final Driver driver;
    @Getter
    private final boolean tryToReuse;
    @Nullable
    private final String instanceName;
    @Getter
    private final NetworkBind networkBind;

    /**
     * Constructor that sets tryToReuse to true
     *
     * @param server       application server definitions
     * @param migration    database migrations definitions
     * @param driver       database driver definitions
     * @param instanceName name of the database instance that will be operated upon
     * @param networkBind  a target network bind to Cascades server
     */
    public Configuration(Server server,
                         Migration migration,
                         Driver driver,
                         @Nullable String instanceName,
                         @Nullable NetworkBind networkBind) {
        this(server, migration, driver, true, instanceName, networkBind);
    }

    /**
     * Default constructor
     *
     * @param server       application server definitions
     * @param migration    database migrations definitions
     * @param driver       database driver definitions
     * @param tryToReuse   information if database should be try to reused
     * @param instanceName name of the database instance that will be operated upon
     * @param networkBind  a target network bind to Cascades server
     */
    public Configuration(Server server,
                         Migration migration,
                         Driver driver,
                         boolean tryToReuse,
                         @Nullable String instanceName,
                         @Nullable NetworkBind networkBind) {
        this.server = server;
        this.migration = migration;
        this.driver = driver;
        this.tryToReuse = tryToReuse;
        this.instanceName = instanceName;
        this.networkBind = Optional
            .fromNullable(networkBind)
            .or(DEFAULT_NETWORK_BIND);
    }

    /**
     * A getter for database instance name
     *
     * @return an optional instance name
     */
    public Optional<String> getInstanceName() {
        return Optional.fromNullable(instanceName);
    }

    @RequiredArgsConstructor
    @Getter
    private static final class SimpleNetworkBind implements NetworkBind {
        private final String host;
        private final int port;
    }
}
