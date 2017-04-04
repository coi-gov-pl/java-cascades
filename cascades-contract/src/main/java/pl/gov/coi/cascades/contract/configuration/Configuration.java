package pl.gov.coi.cascades.contract.configuration;

import com.google.common.base.Optional;
import lombok.Getter;
import pl.gov.coi.cascades.supplier.uri.SchemeHostPortUri;

import javax.annotation.Nullable;
import java.net.URI;


/**
 * Configuration class for all Cascades operations
 */
public class Configuration {

    private static final String DEFAULT_SCHEME = "http";
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 7890;
    private static final URI DEFAULT_SERVER_URI = new SchemeHostPortUri(
        DEFAULT_SCHEME, DEFAULT_HOST, DEFAULT_PORT
    ).get();

    @Getter
    private final Driver driver;
    @Getter
    private final boolean tryToReuse;
    @Getter
    private final URI cascadesServerUri;
    @Getter
    private final long timeoutInSeconds;
    @Nullable
    private final Server server;
    @Nullable
    private final Migration migration;
    @Nullable
    private final String instanceName;

    /**
     * Default constructor
     *
     * @param driver           database driver definitions
     * @param tryToReuse       information if database should be try to reused
     * @param timeoutInSeconds a timeout given in seconds to remote operations to complete
     * @param server           application server definitions
     * @param migration        database migrations definitions
     * @param instanceName     name of the database instance that will be operated upon
     * @param cascadesServerUri      a URI Cascades server
     */
    public Configuration(Driver driver,
                         boolean tryToReuse,
                         long timeoutInSeconds,
                         @Nullable Server server,
                         @Nullable Migration migration,
                         @Nullable String instanceName,
                         @Nullable URI cascadesServerUri) {
        this.driver = driver;
        this.tryToReuse = tryToReuse;
        this.timeoutInSeconds = timeoutInSeconds;
        this.server = server;
        this.migration = migration;
        this.instanceName = instanceName;
        this.cascadesServerUri = Optional
            .fromNullable(cascadesServerUri)
            .or(DEFAULT_SERVER_URI);
    }

    /**
     * A getter for database instance name
     *
     * @return an optional instance name
     */
    public Optional<String> getInstanceName() {
        return Optional.fromNullable(instanceName);
    }

    /**
     * A getter for migration object
     * @return a optional instance of migration
     */
    public Optional<Migration> getMigration() {
        return Optional.fromNullable(migration);
    }

    /**
     * A getter for server object.
     *
     * @return a optional instance of server object
     */
    public Optional<Server> getServer() {
        return Optional.fromNullable(server);
    }

}
