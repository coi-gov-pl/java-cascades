package pl.gov.coi.cascades.server;

import lombok.AllArgsConstructor;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseOperations;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;
import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;

import java.util.List;

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
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    private NetworkBind getNetworkBind(String templateId) {
        Template template =
            templateIdGateway.find(templateId).isPresent() ? templateIdGateway.find(templateId).get() : null;

        if (template != null) {
            List<ServerDef> managedServers = serverConfigurationService.getManagedServers();
            for (ServerDef managedServer : managedServers) {
                if (managedServer.getServerId().equalsIgnoreCase(template.getServerId())) {
                    return new NetworkBindImpl(managedServer.getHost(), managedServer.getPort());
                }
            }
        }

        throw new EidIllegalArgumentException(
            "20180628:191916",
            "Given templateId with serverID hasn't been found in connection settings."
        );
    }
}
