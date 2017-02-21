package pl.gov.coi.cascades.contract.configuration;

import com.google.common.base.Optional;
import lombok.Getter;
import pl.gov.coi.cascades.contract.domain.NetworkBind;

import javax.annotation.Nullable;

public class Configuration {

    @Getter
    private final Server server;
    @Getter
    private final Migration migration;
    @Getter
    private final Driver driver;
    @Getter
    private final boolean tryToReuse;
    private final Optional<String> instanceName;
    private final Optional<NetworkBind> cascadesServerNetworkBind;
    private static final int DEFAULT_PORT = 7890;
    private static final String DEFAULT_HOST = "localhost";
    private static final NetworkBind DEFAULT_NETWORK_BIND = new NetworkBind() {
        @Override
        public String getHost() {
            return DEFAULT_HOST;
        }

        @Override
        public int getPort() {
            return DEFAULT_PORT;
        }
    };

    public Configuration(Server server,
                         Migration migration,
                         Driver driver,
                         @Nullable String instanceName,
                         @Nullable NetworkBind cascadesServerNetworkBind) {
        this.server = server;
        this.migration = migration;
        this.driver = driver;
        this.tryToReuse = true;
        this.instanceName = Optional.fromNullable(instanceName);
        this.cascadesServerNetworkBind = cascadesServerNetworkBind != null ?
                Optional.of(cascadesServerNetworkBind) :
                Optional.of(DEFAULT_NETWORK_BIND);
    }

    public Configuration(Server server,
                         Migration migration,
                         Driver driver,
                         boolean tryToReuse,
                         @Nullable String instanceName,
                         @Nullable NetworkBind cascadesServerNetworkBind) {
        this.server = server;
        this.migration = migration;
        this.driver = driver;
        this.tryToReuse = tryToReuse;
        this.instanceName = Optional.fromNullable(instanceName);
        this.cascadesServerNetworkBind = cascadesServerNetworkBind != null ?
                Optional.of(cascadesServerNetworkBind) :
                Optional.of(DEFAULT_NETWORK_BIND);
    }

    public Optional<String> getInstanceName() {
        return instanceName;
    }

    public Optional<NetworkBind> getCascadesServerNetworkBind() {
        return cascadesServerNetworkBind;
    }
}