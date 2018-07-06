package pl.gov.coi.cascades.server;

import lombok.AllArgsConstructor;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseOperations;
import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;

import java.util.Optional;

@AllArgsConstructor
public class DatabaseOperationsImpl implements DatabaseOperations {

    private ServerConfigurationService serverConfigurationService;

    @Override
    @Deprecated
    public DatabaseInstance createDatabase(DatabaseInstance databaseInstance) {
        Template template = databaseInstance.getTemplate();
        if (template != null) {

            DatabaseInstance databaseInstanceWithSettings = databaseInstance
                .setNetworkBind(getNetworkBind(template));
            // TODO: write implementation
            return databaseInstanceWithSettings;
        }

        throw new EidIllegalArgumentException(
            "20180706:151316",
            "Template hasn't been found.");
    }

    @Override
    @Deprecated
    public void deleteDatabase(DatabaseInstance databaseInstance) {
        // TODO: write implementation
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    private NetworkBind getNetworkBind(Template template) {
        ServerDef serverDef = findServerDef(template);

        if (serverDef != null) {
            return new NetworkBindImpl(serverDef.getHost(), serverDef.getPort());
        }

        throw new EidIllegalArgumentException(
            "20180628:191916",
            "Hasn't been found connection settings."
        );
    }

    private ServerDef findServerDef(Template template) {
        Optional<ServerDef> correctServerDef = serverConfigurationService
            .getManagedServers()
            .stream()
            .filter(p -> p.getServerId()
                .equalsIgnoreCase(template.getServerId())
            )
            .findFirst();

        return correctServerDef.orElse(null);
    }
}
