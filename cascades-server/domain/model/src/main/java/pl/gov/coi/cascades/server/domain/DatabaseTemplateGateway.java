package pl.gov.coi.cascades.server.domain;

import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.contract.domain.Template;

import java.nio.file.Path;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 28.06.17
 */
public interface DatabaseTemplateGateway {

    /**
     * Creates a new template for templateId
     * @param template
     * @param deploySQLScriptPath
     */
    void createTemplate(Template template, Path deploySQLScriptPath);

    void deleteTemplate(Template template);

    boolean canBeRemoved(Template template);

}
