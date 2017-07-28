package pl.gov.coi.cascades.server;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
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
 * @since 28.07.17
 */
public class PostgresTemplateGateway implements DatabaseTemplateGateway {

    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(PostgresTemplateGateway.class);
    private Logger logger;
    private DatabaseManager databaseManager;

    PostgresTemplateGateway(DatabaseManager databaseManager) {
        this(
            DEFAULT_LOGGER,
            databaseManager
        );
    }

    @VisibleForTesting
    PostgresTemplateGateway(Logger logger,
                            DatabaseManager databaseManager) {
        this.logger = logger;
        this.databaseManager = databaseManager;
    }

    @Override
    public void createTemplate(Template template, Path deploySQLScriptPath) {
        try {
            JdbcTemplate jdbcTemplate = databaseManager.get(template.getServerId());
            String script = readFileAsString(deploySQLScriptPath);
            String[] queries = script.split(";");
            String createQuery = String.format(
                "CREATE DATABASE %s TEMPLATE template0",
                template.getName()
            );
            String alterQuery = "";

            jdbcTemplate.execute(createQuery);
            jdbcTemplate.execute(alterQuery);

            for (String str : queries) {
                jdbcTemplate.execute(str);
            }
        } catch (SQLException e) {
            throw new EidIllegalArgumentException("20170728:130448", e);
        }
    }

    private static String readFileAsString(Path deploySQLScriptPath) {
        StringBuilder fileData = new StringBuilder();

        try (Stream<String> lines = Files.lines(deploySQLScriptPath)) {
            lines.forEach(fileData::append);
        } catch (IOException e) {
            throw new EidIllegalStateException("20170728:130520", e);
        }
        return fileData.toString();
    }

    @Override
    public void deleteTemplate(Template template) {
        try {
            JdbcTemplate jdbcTemplate = databaseManager.get(template.getServerId());
            String disconnectQuery = "";
            String deleteQuery = "";

            jdbcTemplate.execute(disconnectQuery);
            jdbcTemplate.execute(deleteQuery);
        } catch (SQLException e) {
            throw new EidIllegalArgumentException("20170726:135511", e);
        }
    }

    @Override
    public boolean canBeRemoved(Template template) {
        return false;
    }
}
