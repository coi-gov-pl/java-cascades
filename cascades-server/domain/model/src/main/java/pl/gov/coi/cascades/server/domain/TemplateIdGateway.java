package pl.gov.coi.cascades.server.domain;

import pl.gov.coi.cascades.contract.domain.TemplateId;

import javax.annotation.Nullable;
import java.util.Optional;

public interface TemplateIdGateway {

    /**
     * Method finds an optional of template of for given template of id.
     *
     * @param templateId Given template of id.
     * @return An optional of template of id.
     */
    Optional<TemplateId> find(@Nullable String templateId);

}
