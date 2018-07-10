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

    private static final String ORACLE = "oracle";
    private static final String POSTGRESQL = "postgresql";
    private DatabaseManager databaseManager;

    @Override
    public void createUserPostgres(DatabaseInstance databaseInstance) {
        ConnectionDatabase connectionDatabase = createConnection(databaseInstance.getTemplate());
        if (connectionDatabase.getType().contains(POSTGRESQL)) {
            StringBuilder postgresStartCommands = getPostgresStartCommands(databaseInstance);
            runSemicolonSeperatedSQL(connectionDatabase, postgresStartCommands.toString());
        }
    }

    @Override
    public void deleteUser(DatabaseInstance databaseInstance) {
        ConnectionDatabase connectionDatabase = createConnection(databaseInstance.getTemplate());
        StringBuilder commands = new StringBuilder();

        if (connectionDatabase.getType().contains(ORACLE)) {
            commands.append(getOracleDeleteUserCommands());
        } else if (connectionDatabase.getType().contains(POSTGRESQL)) {
            UsernameAndPasswordCredentials credentials = databaseInstance.getCredentials();
            commands.append(
                    getPostgresDeleteUserCommands(databaseInstance.getTemplate().getName(),
                    credentials.getUsername())
            );
        }

        runSemicolonSeperatedSQL(connectionDatabase, commands.toString());
    }

    private ConnectionDatabase createConnection(Template template) {
        try {
            return databaseManager.getConnectionToServer(template.getServerId());
        } catch (SQLException e) {
            throw new EidIllegalArgumentException("20180704:102108", e);
        }
    }

    private StringBuilder getPostgresStartCommands(DatabaseInstance databaseInstance) {
        StringBuilder command = new StringBuilder();
        UsernameAndPasswordCredentials credentials = databaseInstance.getCredentials();

        command.append(getPostgresCreateUserCommand(credentials));
        command.append(getPostgresPermissionsCommand(
            databaseInstance.getTemplate().getName(),
            credentials.getUsername())
        );

        return command;
    }


    private String getPostgresCreateUserCommand(UsernameAndPasswordCredentials credentials) {
      return String.format(
            "CREATE USER %s WITH ENCRYPTED PASSWORD '%s';",
            credentials.getUsername(),
            credentials.getPassword()
        );
    }

    private String getPostgresPermissionsCommand(String templateName, String username) {
        return String.format(
            "GRANT ALL PRIVILEGES ON DATABASE %s TO %s;",
            templateName,
            username
        );
    }

    @Deprecated
    private String getOracleDeleteUserCommands() {
        // TODO: write an implementation
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    private String getPostgresDeleteUserCommands(String templateName, String username) {
        StringBuilder commands = new StringBuilder();
        commands.append(String.format(
            "REVOKE ALL PRIVILEGES ON ALL TABLES IN SCHEMA public FROM %s;",
            username
        )).append(String.format(
            "REVOKE ALL ON DATABASE %s FROM %s;",
            templateName,
            username
        )).append(String.format(
            "DROP USER %s;",
            username
        ));

        return commands.toString();
    }

    private void runSemicolonSeperatedSQL(ConnectionDatabase connectionDatabase, String createDatabaseCommand) {
        String[] queries = createDatabaseCommand.split(";");
        for (String str : queries) {
            connectionDatabase.getJdbcTemplate().execute(str);
        }
    }
}
