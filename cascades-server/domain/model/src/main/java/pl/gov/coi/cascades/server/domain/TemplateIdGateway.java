package pl.gov.coi.cascades.server.domain;

import pl.gov.coi.cascades.contract.domain.Template;

import java.util.Optional;

public interface TemplateIdGateway {

    /**
     * Method finds an optional of template of for given template of id.
     *
     * @param templateId Given template of id.
     * @return An optional of template of id.
     */
    Optional<Template> find(String templateId);

    /**
     * Method gives an optional of template id.
     *
     * @return An optional of template of id.
     */
    Optional<Template> getDefaultTemplateId();

    /**
     * Method saves given templateId.
     *
     * @param template Given templateId to addTemplate.
     */
    void addTemplate(Template template);
}
