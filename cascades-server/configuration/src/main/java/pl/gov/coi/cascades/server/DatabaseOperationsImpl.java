package pl.gov.coi.cascades.server;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseOperations;
import pl.gov.coi.cascades.server.domain.DatabaseTypeImpl;
import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;

import java.sql.SQLException;
import java.util.Optional;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
@AllArgsConstructor
public class DatabaseOperationsImpl implements DatabaseOperations {

    private ServerConfigurationService serverConfigurationService;
    private DatabaseManager databaseManager;

    @Override
    @Deprecated
    public DatabaseInstance createDatabase(DatabaseInstance databaseInstance) {
        Template template = databaseInstance.getTemplate();
        if (template != null) {
            DatabaseInstance databaseInstanceWithSettings = databaseInstance
                .setNetworkBind(getNetworkBind(template))
                .setDatabaseType(getDatabaseType(template));

            createInstance(databaseInstanceWithSettings, template);

            return databaseInstanceWithSettings;
        }

        throw new EidIllegalArgumentException(
            "20180706:151316",
            "Template hasn't been found."
        );
    }

    private void createInstance(DatabaseInstance databaseInstance, Template template) {
        String databaseType = databaseInstance.getDatabaseType().getName();

        if (databaseType.contains("oracle")) {
            createScriptOracle(template);
        } else if (databaseType.contains("postgresql")) {
            createScriptPostgres(template);
        }

        runSemicolonSeperatedSQL(connectionDatabase, createDatabaseCommand);
    }

    private void createScriptOracle(Template template) {
        try {
            ConnectionDatabase connectionToTemplate = databaseManager.getConnectionToTemplate(
                template.getServerId(),
                template.getName()
            );

            getOracleCreateInstanceCommands(template);
            getOracleCreateUserCommands(template);

            runSemicolonSeperatedSQL(connectionToTemplate, );


        } catch (SQLException e) {
            throw new EidIllegalArgumentException("20180710:162721", e);
        }
    }

    @Override
    @Deprecated
    public void deleteDatabase(DatabaseInstance databaseInstance) {
        // TODO: write implementation
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    private NetworkBind getNetworkBind(Template template) {
        ServerDef serverDef = findServerDef(template);
        String host = serverDef.getHost();
        int port = serverDef.getPort();

        if (StringUtils.isNotBlank(host) && port != 0) {
            return new NetworkBindImpl(host, port);
        }

        throw new EidIllegalArgumentException(
            "20180628:191916",
            "Hasn't been found connection settings."
        );
    }

    private DatabaseType getDatabaseType(Template template) {
        ServerDef serverDef = findServerDef(template);
        if (serverDef != null && StringUtils.isNotBlank(serverDef.getType())) {
            return new DatabaseTypeImpl(serverDef.getType());
        }

        throw new EidIllegalArgumentException(
            "20180706:151716",
            "Hasn't been found database type."
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

    private void runSemicolonSeperatedSQL(ConnectionDatabase connectionDatabase, String createDatabaseCommand) {
        String[] queries = createDatabaseCommand.split(";");
        for (String str : queries) {
            connectionDatabase.getJdbcTemplate().execute(str);
        }
    }
}
