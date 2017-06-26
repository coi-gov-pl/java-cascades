package pl.gov.coi.cascades.server.domain;

import pl.gov.coi.cascades.contract.domain.TemplateId;

import java.util.Optional;

public interface TemplateIdGateway {

    /**
     * Method finds an optional of template of for given template of id.
     *
     * @param templateId Given template of id.
     * @return An optional of template of id.
     */
    Optional<TemplateId> find(String templateId);

    /**
     * Method gives an optional of template id.
     *
     * @return An optional of template of id.
     */
    Optional<TemplateId> getDefaultTemplateId();

    /**
     * Method saves given templateId.
     *
     * @param templateId Given templateId to save.
     */
    void save(TemplateId templateId);

}
