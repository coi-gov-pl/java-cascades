package pl.gov.coi.cascades.server;

import lombok.AllArgsConstructor;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseOperations;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;
import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;

import java.util.Optional;

@AllArgsConstructor
public class DatabaseOperationsImpl implements DatabaseOperations {

    private ServerConfigurationService serverConfigurationService;
    private TemplateIdGateway templateIdGateway;

    @Override
    @Deprecated
    public NetworkBind createDatabase(DatabaseInstance databaseInstance) {
        String templateId = databaseInstance.getTemplate().getId();
        NetworkBind networkBind = getNetworkBind(templateId);
        // TODO: write implementation

        return networkBind;
    }

    @Override
    @Deprecated
    public void deleteDatabase(DatabaseInstance databaseInstance) {
        // TODO: write implementation
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    private NetworkBind getNetworkBind(String templateId) {
        Optional<Template> templateOptional = templateIdGateway.find(templateId);
        Template template = templateOptional != null ? templateOptional.orElse(null) : null;

        if (template != null) {
            Optional<ServerDef> correctServerDef = serverConfigurationService
                .getManagedServers()
                .stream()
                .filter(p -> p.getServerId()
                    .equalsIgnoreCase(template.getServerId())
                )
                .findFirst();

            ServerDef serverDef = correctServerDef.orElse(null);

            if (serverDef != null) {
                return new NetworkBindImpl(serverDef.getHost(), serverDef.getPort());
            }
        }

        throw new EidIllegalArgumentException(
            "20180628:191916",
            "Given templateId with serverID hasn't been found in connection settings."
        );
    }
}
