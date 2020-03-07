package pl.gov.coi.cascades.server;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseOperationsGateway;
import pl.gov.coi.cascades.server.domain.DatabaseTypeImpl;
import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;

import java.sql.SQLException;
import java.util.Optional;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
@AllArgsConstructor
public class GeneralDatabaseOperationGateway implements DatabaseOperationsGateway {

    private static final String ORACLE = "ora12c";
    private static final String POSTGRESQL = "pgsql";

    private ServerConfigurationService serverConfigurationService;
    private DatabaseManager databaseManager;

    @Override
    public DatabaseInstance createDatabase(DatabaseInstance databaseInstance) {
        Template template = databaseInstance.getTemplate();
        if (template != null) {
            DatabaseInstance databaseInstanceWithSettings = databaseInstance
                .setNetworkBind(getNetworkBind(template))
                .setDatabaseType(getDatabaseType(template));

            createDatabseInstance(databaseInstanceWithSettings);

            return databaseInstanceWithSettings;
        }

        throw new EidIllegalArgumentException(
            "20180706:151316",
            "Template hasn't been found."
        );
    }

    @Override
    public void deleteDatabase(DatabaseInstance databaseInstance) {
        String databaseType = databaseInstance.getDatabaseType().getName();
        ConnectionDatabase connectionDatabase = createConnectionToServer(databaseInstance.getTemplate());

        if (databaseType.contains(POSTGRESQL)) {
            String postgresCreateCommands = getPostgresDeleteCommands(databaseInstance.getDatabaseName());
            runSemicolonSeperatedSQL(connectionDatabase, postgresCreateCommands);
        } else if (databaseType.contains(ORACLE)) {
            String oracleCreateCommands = getOracleDeleteCommands(databaseInstance.getDatabaseName());
            runSemicolonSeperatedSQL(connectionDatabase, oracleCreateCommands);

        } else {
            throw new EidIllegalArgumentException(
                "20180711:120816",
                "Hasn't been found database type."
            );
        }
    }

    private void createDatabseInstance(DatabaseInstance databaseInstance) {
        String databaseType = databaseInstance.getDatabaseType().getName();
        ConnectionDatabase connectionDatabase = createConnectionToServer(databaseInstance.getTemplate());

        if (databaseType.contains(POSTGRESQL)) {
            String postgresCreateCommands = getPostgresCreateCommands(databaseInstance);
            runSemicolonSeperatedSQL(connectionDatabase, postgresCreateCommands);
        } else if (databaseType.contains(ORACLE)) {
            String oracleCreateCommands = getOracleCreateCommands(databaseInstance);
            runSemicolonSeperatedSQL(connectionDatabase, oracleCreateCommands);

        } else {
            throw new EidIllegalArgumentException(
                "20180713:104116",
                "Hasn't been found database type."
            );
        }
    }

    private String getPostgresCreateCommands(DatabaseInstance databaseInstance) {
        String templateName = databaseInstance.getTemplate().getName();
        String databaseName = databaseInstance.getDatabaseName();

        return String.format("CREATE DATABASE %s TEMPLATE %s",
            databaseName,
            templateName
        );
    }

    private String getOracleCreateCommands(DatabaseInstance databaseInstance) {
        String templateName = databaseInstance.getTemplate().getName();
        String databaseName = databaseInstance.getDatabaseName();
        return new StringBuilder()
            .append("ALTER SESSION SET container = CDB$ROOT;")
            .append(String.format("CREATE PLUGGABLE DATABASE %s from %s",
                databaseName,
                templateName))
            .append(String.format(
                " file_name_convert = ('/u01/app/oracle/oradata/orcl12c/%s', '/u01/app/oracle/oradata/orcl12c/%s');",
                templateName,
                databaseName
            )).append(String.format(
                "ALTER PLUGGABLE DATABASE %s OPEN READ WRITE;",
                databaseName
            )).toString();
    }

    private String getPostgresDeleteCommands(String databaseName) {
        return new StringBuilder()
            .append(String.format(
                "DROP DATABASE %s;",
                databaseName
            )).toString();
    }

    private String getOracleDeleteCommands(String databaseName) {
        return new StringBuilder()
            .append("ALTER SESSION SET container = CDB$ROOT;")
            .append(String.format(
                "ALTER PLUGGABLE DATABASE %s CLOSE IMMEDIATE;",
                databaseName
            )).append(String.format(
                "DROP PLUGGABLE DATABASE %s INCLUDING DATAFILES;",
                databaseName
            )).toString();
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

    private ConnectionDatabase createConnectionToServer(Template template) {
        try {
            return databaseManager.getConnectionToServer(template.getServerId());
        } catch (SQLException e) {
            throw new EidIllegalArgumentException("20180711:114808", e);
        }
    }

    private void runSemicolonSeperatedSQL(ConnectionDatabase connectionDatabase, String createDatabaseCommand) {
        String[] queries = createDatabaseCommand.split(";");
        for (String str : queries) {
            connectionDatabase.getJdbcTemplate().execute(str);
        }
    }
}
