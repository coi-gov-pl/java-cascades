package pl.gov.coi.cascades.server.domain;

import pl.gov.coi.cascades.contract.domain.Template;

import java.nio.file.Path;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 28.06.17
 */
public interface DatabaseTemplateGateway {

    /**
     * Creates a new template for templateId.
     *
     * @param template            Given template.
     * @param deploySQLScriptPath Given path to deploy script.
     */
    void createTemplate(Template template, Path deploySQLScriptPath);

    /**
     * Deletes given template.
     *
     * @param template Given template.
     */
    void deleteTemplate(Template template);

    /**
     * Checks if given template can be removed.
     *
     * @param template Given template.
     * @return Information if given template can be removed.
     */
    boolean canBeRemoved(Template template);

}
