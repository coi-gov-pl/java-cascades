package pl.gov.coi.cascades.server.presentation.launchdatabase;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.contract.service.RemoteDatabaseRequest;

import javax.annotation.Nullable;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 03.03.17.
 */
final class RemoteDatabaseRequestDTO extends RemoteDatabaseRequest {
    private static final long serialVersionUID = 321782881132L;
    /**
     * {@inheritDoc}
     */
    public RemoteDatabaseRequestDTO(@JsonProperty("type") String type,
                                    @JsonProperty("templateId") @Nullable String templateId,
                                    @JsonProperty("instanceName") @Nullable String instanceName) {
        super(type, createTemplateId(templateId), instanceName);
    }

    @Nullable
    private static Template createTemplateId(@Nullable String templateId) {
        return templateId == null
            ? null
            : new InputTemplate(templateId);
    }
}
