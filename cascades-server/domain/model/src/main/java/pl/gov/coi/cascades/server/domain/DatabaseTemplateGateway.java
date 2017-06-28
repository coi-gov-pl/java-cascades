package pl.gov.coi.cascades.server.domain;

import pl.gov.coi.cascades.contract.domain.TemplateId;

import java.nio.file.Path;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 28.06.17
 */
public interface DatabaseTemplateGateway {

    /**
     * Creates a new template for templateId
     * @param templateId
     * @param deploySQLScriptPath
     */
    void createTemplate(TemplateId templateId, Path deploySQLScriptPath);

    void deleteTemplate(TemplateId templateId);

    boolean canBeRemoved(TemplateId templateId);

}
