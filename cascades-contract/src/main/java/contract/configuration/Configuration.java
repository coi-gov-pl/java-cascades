package contract.configuration;

import com.google.common.base.Optional;
import com.sun.istack.internal.Nullable;
import contract.domain.NetworkBind;
import lombok.Getter;

@Getter
public class Configuration {

    private final Optional<String> instanceName;
    private final NetworkBind cascadesServerNetworkBind;
    private final Server server;
    private final Migration migration;
    private final Driver driver;
    private boolean tryToReuse;

    public Configuration(@Nullable String instanceName,
                         NetworkBind cascadesServerNetworkBind,
                         Server server,
                         Migration migration,
                         Driver driver) {
        this.instanceName = Optional.fromNullable(instanceName);
        this.cascadesServerNetworkBind = cascadesServerNetworkBind;
        this.server = server;
        this.migration = migration;
        this.driver = driver;
        this.tryToReuse = true;
    }

}