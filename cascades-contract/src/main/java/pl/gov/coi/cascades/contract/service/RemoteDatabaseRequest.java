package pl.gov.coi.cascades.contract.service;

import com.google.common.base.Optional;
import lombok.Getter;
import pl.gov.coi.cascades.contract.domain.TemplateId;

import javax.annotation.Nullable;
import java.io.Serializable;

/**
 * This is request of creation of remote database instance
 */
public class RemoteDatabaseRequest implements Serializable {

    private static final long serialVersionUID = 42L;

    @Getter
    private final String type;
    @Nullable
    private final TemplateId templateId;
    @Nullable
    private final String instanceName;

    /**
     * Required argument constructor.
     *
     * @param type         Given type of database (a name or FQCN).
     * @param templateId   Given id of template (Optional).
     * @param instanceName Given name of instance (Optional).
     */
    public RemoteDatabaseRequest(String type,
                                 @Nullable TemplateId templateId,
                                 @Nullable String instanceName) {
        this.type = type;
        this.templateId = templateId;
        this.instanceName = instanceName;
    }

    /**
     * Method gives optional name of instance.
     *
     * @return Name of instance.
     */
    public Optional<String> getInstanceName() {
        return Optional.fromNullable(instanceName);
    }

    /**
     * Getter for optional {@link TemplateId}
     *
     * @return an optional template id
     */
    public Optional<TemplateId> getTemplateId() {
        return Optional.fromNullable(templateId);
    }
}
