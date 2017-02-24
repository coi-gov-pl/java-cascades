package pl.gov.coi.cascades.server.domain;

import com.google.common.base.Optional;
import pl.gov.coi.cascades.contract.domain.TemplateId;

public interface TemplateIdGateway {

    /**
     * Method finds an optional of template of for given template of id.
     *
     * @param templateId Given template of id.
     * @return An optional of template of id.
     */
    Optional<TemplateId> find(String templateId);

}
