package pl.gov.coi.cascades.server.persistance.hibernate;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.server.domain.DatabaseTemplateGateway;
import pl.wavesoftware.eid.exceptions.Eid;

import java.nio.file.Path;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 28.06.17
 */
@AllArgsConstructor
public class DatabaseTemplateGatewayImpl implements DatabaseTemplateGateway {

    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(DatabaseTemplateGateway.class);
    private Logger logger;

    DatabaseTemplateGatewayImpl() {
        this(DEFAULT_LOGGER);
    }

    @Override
    public void createTemplate(Template template, Path deploySQLScriptPath) {
        if (logger.isInfoEnabled()) {
            logger.info(new Eid("20170628:133136")
                .makeLogMessage(
                    "Script, loaded in " + deploySQLScriptPath + " has been saved."
                )
            );
        }
    }

    @Override
    public void deleteTemplate(Template template) {
        if (logger.isInfoEnabled()) {
            logger.info(new Eid("20170629:082315")
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
