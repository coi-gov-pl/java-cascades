package pl.gov.coi.cascades.server;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.server.domain.DatabaseTemplateGateway;
import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.stream.Stream;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 28.06.17
 */
public class GeneralTemplateGateway implements DatabaseTemplateGateway {

    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(GeneralTemplateGateway.class);
    private static final String ORACLE = "oracle";
    private static final String POSTGRESQL = "postgresql";
    private Logger logger;
    private DatabaseManager databaseManager;

    GeneralTemplateGateway(DatabaseManager databaseManager) {
        this(
            DEFAULT_LOGGER,
            databaseManager
        );
    }

    @VisibleForTesting
    GeneralTemplateGateway(Logger logger,
                           DatabaseManager databaseManager) {
        this.logger = logger;
        this.databaseManager = databaseManager;
    }

    @Override
    public void createTemplate(Template template, Path deploySQLScriptPath) {
        try {
            ConnectionDatabase connectionDatabase = databaseManager.getConnectionToServer(template.getServerId());
            runCreateDatabaseCommand(connectionDatabase, template);
            ConnectionDatabase connectionToTemplate = databaseManager.getConnectionToDatabase(template.getServerId(), template.getName());
            runDeployScript(connectionToTemplate, deploySQLScriptPath);
            runAfterDeployScript(connectionToTemplate, connectionDatabase, template);
        } catch (SQLException e) {
            throw new EidIllegalArgumentException("20170711:151221", e);
        }
    }

    private void runDeployScript(ConnectionDatabase connectionToTemplate, Path deploySQLScriptPath) {
        String deployScript = readFileAsString(deploySQLScriptPath);
        runSemicolonSeperatedSQL(connectionToTemplate, deployScript);
    }

    private void runAfterDeployScript(ConnectionDatabase connectionToTemplate, ConnectionDatabase connectionDatabase, Template template) {
        if (connectionToTemplate.getType().contains(POSTGRESQL)) {
            runSemicolonSeperatedSQL(connectionToTemplate, String.format(
                "UPDATE pg_database SET datistemplate = TRUE WHERE datname = '%s';" +
                    "UPDATE pg_database SET datallowconn = FALSE WHERE datname = '%s'",
                template.getName(), template.getName()
            ));
        } else if (connectionDatabase.getType().contains(ORACLE)) {
            runSemicolonSeperatedSQL(connectionDatabase, String.format(
                "ALTER PLUGGABLE DATABASE %s CLOSE IMMEDIATE;" +
                    "ALTER PLUGGABLE DATABASE %s OPEN READ ONLY;",
                template.getName(), template.getName()
            ));
        }
    }

    private void runCreateDatabaseCommand(ConnectionDatabase connectionDatabase, Template template) {
        String createDatabaseCommand = "";
        if (connectionDatabase.getType().contains(ORACLE)) {
            createDatabaseCommand = getOracleStartCommands(template.getName());
        } else if (connectionDatabase.getType().contains(POSTGRESQL)) {
            createDatabaseCommand = getPostgresStartCommands(template.getName());
        }
        runSemicolonSeperatedSQL(connectionDatabase, createDatabaseCommand);
    }

    private void runSemicolonSeperatedSQL(ConnectionDatabase connectionDatabase, String createDatabaseCommand) {
        String[] queries = createDatabaseCommand.split(";");
        for (String str : queries) {
            connectionDatabase.getJdbcTemplate().execute(str);
        }
    }

    @Override
    public void deleteTemplate(Template template) {
        try {
            ConnectionDatabase connectionDatabase = databaseManager.getConnectionToServer(template.getServerId());
            StringBuilder commands = new StringBuilder();

            if (connectionDatabase.getType().contains(ORACLE)) {
                commands.append(getOracleFinishCommands(template.getName()));
            } else if (connectionDatabase.getType().contains(POSTGRESQL)) {
                commands.append(getPostgresFinishCommands(template.getName()));
            }

            runSemicolonSeperatedSQL(connectionDatabase, commands
                .toString());
        } catch (SQLException e) {
            throw new EidIllegalArgumentException("20170726:135511", e);
        }
    }

    private static String getPostgresFinishCommands(String templateName) {
        return new StringBuilder()
                .append(String.format(
                "UPDATE pg_database SET datistemplate='false' WHERE datname='%s';",
                templateName
                )).append(String.format(
                "DROP DATABASE %s;",
                templateName
        )).toString();
    }

    private static String readFileAsString(Path deploySQLScriptPath) {
        StringBuilder fileData = new StringBuilder();

        try (Stream<String> lines = Files.lines(deploySQLScriptPath)) {
            lines.forEach(fileData::append);
        } catch (IOException e) {
            throw new EidIllegalStateException("20170711:141406", e);
        }
        return fileData.toString();
    }

    private static String getOracleStartCommands(String templateName) {
        return new StringBuilder().append("ALTER SESSION SET container = CDB$ROOT;")
            .append(String.format(
                "CREATE PLUGGABLE DATABASE %s ADMIN USER admin IDENTIFIED BY ksdn#2Hd",
                templateName
            )).append(String.format(
                " file_name_convert = ('/u01/app/oracle/oradata/orcl12c/pdbseed', '/u01/app/oracle/oradata/orcl12c/%s');",
                templateName
            )).append(String.format(
                "ALTER PLUGGABLE DATABASE %s OPEN;",
                templateName
            )).toString();
    }

    private static String getPostgresStartCommands(String templateName) {
        return String.format(
            "CREATE DATABASE %s TEMPLATE template0;",
            templateName
        );
    }

    private static String getOracleFinishCommands(String templateName) {
        return new StringBuilder()
            .append("ALTER SESSION SET container = CDB$ROOT;")
            .append(String.format(
                "ALTER PLUGGABLE DATABASE %s CLOSE IMMEDIATE;",
                templateName
            )).append(String.format(
                "DROP PLUGGABLE DATABASE %s INCLUDING DATAFILES;",
                templateName
            )).toString();
    }

    @Override
    @Deprecated
    public boolean canBeRemoved(Template template) {
        //TODO: implement this
        throw new UnsupportedOperationException();
    }
}
