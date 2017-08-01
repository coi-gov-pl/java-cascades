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
            ConnectionDatabase connectionDatabase = databaseManager.get(template.getServerId());
            StringBuilder commands = new StringBuilder();
            String script = readFileAsString(deploySQLScriptPath);

            if (connectionDatabase.getType().contains("oracle")) {
                commands.append(getOracleStartCommands(template));
            } else if (connectionDatabase.getType().contains("postgresql")) {
                commands.append(getPostgresStartCommands(template));
            }
            String[] queries = commands
                .append(script)
                .toString()
                .split(";");
            for (String str : queries) {
                connectionDatabase.getJdbcTemplate().execute(str);
            }
        } catch (SQLException e) {
            throw new EidIllegalArgumentException("20170711:151221", e);
        }
    }

    @Override
    public void deleteTemplate(Template template) {
        try {
            ConnectionDatabase connectionDatabase = databaseManager.get(template.getServerId());
            StringBuilder commands = new StringBuilder();

            if (connectionDatabase.getType().contains("ora12c")) {
                commands.append(getOracleFinishCommands(template));
            } else if (connectionDatabase.getType().contains("pgsql")) {

            }

            String[] queries = commands
                .toString()
                .split(";");
            for (String str : queries) {
                connectionDatabase.getJdbcTemplate().execute(str);
            }
        } catch (SQLException e) {
            throw new EidIllegalArgumentException("20170726:135511", e);
        }
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

    private static StringBuilder getOracleStartCommands(Template template) {
        StringBuilder commands = new StringBuilder();
        String createQuery = "CREATE PLUGGABLE DATABASE " +
            template.getName() +
            " ADMIN USER admin IDENTIFIED BY ksdn#2Hd;";
        String alterQuery = "ALTER PLUGGABLE DATABASE " +
            template.getName() +
            " OPEN READ WRITE;";
        commands.append(createQuery).append(alterQuery);
        return commands;
    }

    private static StringBuilder getPostgresStartCommands(Template template) {
        StringBuilder commands = new StringBuilder();
        String createQuery = String.format(
            "CREATE DATABASE %s TEMPLATE template0",
            template.getName()
        );
        commands.append(createQuery);
        return commands;
    }

    private static StringBuilder getOracleFinishCommands(Template template) {
        StringBuilder commands = new StringBuilder();
        String disconnectQuery = String.format(
            "ALTER PLUGGABLE DATABASE %s CLOSE IMMEDIATE",
            template.getName()
        );
        String deleteQuery = String.format(
            "DROP PLUGGABLE DATABASE %s INCLUDING DATAFILES",
            template.getName()
        );
        commands.append(disconnectQuery).append(deleteQuery);
        return commands;
    }

    @Override
    public boolean canBeRemoved(Template template) {
        return false;
    }
}
