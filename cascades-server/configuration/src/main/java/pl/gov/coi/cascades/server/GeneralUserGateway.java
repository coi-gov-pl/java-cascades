package pl.gov.coi.cascades.server;

import lombok.AllArgsConstructor;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseUserGateway;
import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;

import java.sql.SQLException;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
@AllArgsConstructor
public class GeneralUserGateway implements DatabaseUserGateway {

    private static final String ORACLE = "ora12c";
    private static final String POSTGRESQL = "pgsql";
    private DatabaseManager databaseManager;

    @Override
    public void createUser(DatabaseInstance databaseInstance) {
        String databaseType = databaseInstance.getDatabaseType().getName();

        if (databaseType.contains(POSTGRESQL)) {
            ConnectionDatabase connectionDatabase = createConnectionToServer(databaseInstance.getTemplate());
            String postgresCommands = getPostgresCreateUserCommands(databaseInstance);
            runSemicolonSeperatedSQL(connectionDatabase, postgresCommands);

        } else if (databaseType.contains(ORACLE)) {
            ConnectionDatabase connectionDatabase = createConnectionToDatabase(databaseInstance);
            String oracleCommands = getOracleCreateUserCommands(databaseInstance);
            runSemicolonSeperatedSQL(connectionDatabase, oracleCommands);
        } else {
            throw new EidIllegalArgumentException(
                "20180711:110816",
                "Hasn't been found database type."
            );
        }
    }

    @Override
    public void deleteUser(DatabaseInstance databaseInstance) {
        String databaseType = databaseInstance.getDatabaseType().getName();

        if (databaseType.contains(POSTGRESQL)) {
            ConnectionDatabase connectionDatabase = createConnectionToServer(databaseInstance.getTemplate());
            String postgresCommands = getPostgresDeleteUserCommands(
                databaseInstance.getDatabaseName(),
                databaseInstance.getCredentials().getUsername()
            );

            runSemicolonSeperatedSQL(connectionDatabase, postgresCommands);

        } else if (databaseType.contains(ORACLE)) {
            ConnectionDatabase connectionDatabase = createConnectionToDatabase(databaseInstance);
            String oracleCommands = getOracleDeleteUserCommands(
                databaseInstance.getCredentials().getUsername()
            );

            runSemicolonSeperatedSQL(connectionDatabase, oracleCommands);
        }

    }

    private ConnectionDatabase createConnectionToServer(Template template) {
        try {
            return databaseManager.getConnectionToServer(template.getServerId());
        } catch (SQLException e) {
            throw new EidIllegalArgumentException("20180704:102108", e);
        }
    }

    private ConnectionDatabase createConnectionToDatabase(DatabaseInstance databaseInstance) {
        try {
            return databaseManager.getConnectionToDatabase(databaseInstance.getTemplate().getServerId(), databaseInstance.getDatabaseName());
        } catch (SQLException e) {
            throw new EidIllegalArgumentException("20180711:095808", e);
        }
    }

    private String getPostgresCreateUserCommands(DatabaseInstance databaseInstance) {
        StringBuilder command = new StringBuilder();
        UsernameAndPasswordCredentials credentials = databaseInstance.getCredentials();

        command.append(getPostgresCreateUser(credentials));
        command.append(getPostgresPermissions(
            databaseInstance.getDatabaseName(),
            credentials.getUsername())
        );

        return command.toString();
    }

    private String getOracleCreateUserCommands(DatabaseInstance databaseInstance) {
        StringBuilder command = new StringBuilder();
        UsernameAndPasswordCredentials credentials = databaseInstance.getCredentials();

        command.append(getOracleCreateUser(credentials));
        command.append(getOraclePermissions(
            credentials.getUsername())
        );

        return command.toString();
    }


    private String getPostgresCreateUser(UsernameAndPasswordCredentials credentials) {
        return String.format(
            "CREATE USER %s WITH ENCRYPTED PASSWORD '%s';",
            credentials.getUsername(),
            credentials.getPassword()
        );
    }

    private String getPostgresPermissions(String databaseName, String username) {
        return String.format(
            "GRANT ALL PRIVILEGES ON DATABASE %s TO %s;",
            databaseName,
            username
        );
    }

    private String getOracleCreateUser(UsernameAndPasswordCredentials credentials) {
        return String.format(
            "CREATE USER %s IDENTIFIED BY \"%s\";",
            credentials.getUsername(),
            credentials.getPassword()
        );
    }

    private String getOraclePermissions(String username) {
        return String.format(
            "GRANT DBA TO %s;",
            username
        );
    }

    private String getPostgresDeleteUserCommands(String databaseName, String username) {
        StringBuilder commands = new StringBuilder();
        commands.append(String.format(
            "REVOKE ALL PRIVILEGES ON ALL TABLES IN SCHEMA public FROM %s;",
            username
        )).append(String.format(
            "REVOKE ALL ON DATABASE %s FROM %s;",
            databaseName,
            username
        )).append(String.format(
            "DROP USER %s;",
            username
        ));

        return commands.toString();
    }

    private String getOracleDeleteUserCommands(String username) {
        return String.format(
            "DROP USER %s;",
            username
        );

    }

    private void runSemicolonSeperatedSQL(ConnectionDatabase connectionDatabase, String createDatabaseCommand) {
        String[] queries = createDatabaseCommand.split(";");
        for (String str : queries) {
            connectionDatabase.getJdbcTemplate().execute(str);
        }
    }
}
