package pl.gov.coi.cascades.server;

import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseUserGateway;
import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;

import java.sql.SQLException;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
public class GeneralUserGateway implements DatabaseUserGateway {

    private static final String ORACLE = "oracle";
    private static final String POSTGRESQL = "postgresql";
    private DatabaseManager databaseManager;

    public GeneralUserGateway(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void createUser(DatabaseInstance databaseInstance) {
        ConnectionDatabase connectionDatabase = createConnection(databaseInstance.getTemplate());
        StringBuilder correctCommand = getCorrectCommand(connectionDatabase.getType(), databaseInstance);

    }

    @Override
    public void deleteUser(DatabaseInstance databaseInstance) {

    }

    private StringBuilder getCorrectCommand(String databaseType, DatabaseInstance databaseInstance) {
        StringBuilder commands = new StringBuilder();

        if (databaseType.contains(ORACLE)) {
            commands.append(getOracleStartCommands(databaseInstance));
        } else if (databaseType.contains(POSTGRESQL)) {
            commands.append(getPostgresStartCommands(databaseInstance));
        }
        return commands;
    }

    private ConnectionDatabase createConnection(Template template) {
        try {
            return databaseManager.get(template.getServerId());
        } catch (SQLException e) {
            throw new EidIllegalArgumentException("20180704:102108", e);
        }
    }

    private StringBuilder getPostgresStartCommands(DatabaseInstance databaseInstance) {
        StringBuilder command = new StringBuilder();
        UsernameAndPasswordCredentials credentials = databaseInstance.getCredentials();

        command.append(getPostgresCreateUserCommand(credentials));
        command.append(getPostgresPermissionsCommand(databaseInstance.getDatabaseName(), credentials.getUsername()));

        return command;
    }


    private String getPostgresCreateUserCommand(UsernameAndPasswordCredentials credentials) {
      return String.format(
            "CREATE USER %s WITH ENCRYPTED PASSWORD %s;",
            credentials.getUsername(),
            credentials.getPassword()
        );
    }

    private String getPostgresPermissionsCommand(String databaseName, String username) {
        return String.format(
            "GRANT ALL PRIVILEGES ON DATABASE %s TO %s;",
            databaseName,
            username
        );
    }

    private StringBuilder getOracleStartCommands(DatabaseInstance databaseInstance) {
        StringBuilder command = new StringBuilder();
        UsernameAndPasswordCredentials credentials = databaseInstance.getCredentials();

        command.append(getOracleCreateUserCommand(credentials));
        command.append(getOraclePermissionsCommand(databaseInstance.getDatabaseName(), credentials.getUsername()));

        return command;
    }


    private String getOracleCreateUserCommand(UsernameAndPasswordCredentials credentials) {
        return String.format(
            "CREATE USER %s IDENTIFIED BY %s;",
            credentials.getUsername(),
            credentials.getPassword()
        );
    }

    private String getOraclePermissionsCommand(String databaseName, String username) {
        return String.format(
            //"GRANT ALL PRIVILEGES ON DATABASE %s TO %s;",
            databaseName,
            username
        );
    }
}
