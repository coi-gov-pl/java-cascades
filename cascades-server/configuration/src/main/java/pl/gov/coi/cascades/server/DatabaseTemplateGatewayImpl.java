package pl.gov.coi.cascades.server;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.server.domain.DatabaseTemplateGateway;
import pl.wavesoftware.eid.exceptions.Eid;
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
public class DatabaseTemplateGatewayImpl implements DatabaseTemplateGateway {

    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(DatabaseTemplateGatewayImpl.class);
    private Logger logger;
    private DatabaseManager databaseManager;

    DatabaseTemplateGatewayImpl(DatabaseManager databaseManager) {
        this(
            DEFAULT_LOGGER,
            databaseManager
        );
    }

    @VisibleForTesting
    DatabaseTemplateGatewayImpl(Logger logger,
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
            String createQuery = "CREATE PLUGGABLE DATABASE " +
                template.getName() +
                " ADMIN USER admin IDENTIFIED BY ksdn#2Hd";
            String alterQuery = "ALTER PLUGGABLE DATABASE " +
                template.getName() +
                " OPEN READ WRITE";

            jdbcTemplate.execute(createQuery);
            jdbcTemplate.execute(alterQuery);

            for (String str : queries) {
                jdbcTemplate.execute(str);
            }
        } catch (SQLException e) {
            throw new EidIllegalArgumentException("20170711:151221", e);
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

    @Override
    public void deleteTemplate(Template template) {
        if (logger.isInfoEnabled()) {
            logger.info(new Eid("20170629:083716")
                .makeLogMessage(
                    "Given template has been successfully deleted."
                )
            );
        }
    }

    @Override
    public boolean canBeRemoved(Template template) {
        return false;
    }
}
