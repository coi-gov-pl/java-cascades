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
	private final String typeClassName;
    @Nullable
    private final TemplateId templateId;
	@Nullable
	private final String instanceName;

	/**
	 * Required argument constructor.
	 * @param typeClassName Given type of database (a class name).
     * @param templateId Given id of template (Optional).
     * @param instanceName Given name of instance (Optional).
	 */
	public RemoteDatabaseRequest(String typeClassName,
                                 @Nullable TemplateId templateId,
								 @Nullable String instanceName) {
		this.typeClassName = typeClassName;
        this.templateId = templateId;
		this.instanceName = instanceName;
	}

	/**
	 * Method gives optional name of instance.
	 * @return Name of instance.
	 */
	public Optional<String> getInstanceName() {
		return Optional.fromNullable(instanceName);
	}

    /**
     * Getter for optional {@link TemplateId}
     * @return an optional template id
     */
    public Optional<TemplateId> getTemplateId() {
        return Optional.fromNullable(templateId);
    }
}
