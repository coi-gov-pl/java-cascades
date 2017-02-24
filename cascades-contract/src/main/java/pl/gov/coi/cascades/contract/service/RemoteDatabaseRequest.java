package pl.gov.coi.cascades.contract.service;

import com.google.common.base.Optional;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.TemplateId;
import lombok.Getter;

import javax.annotation.Nullable;

/**
 * This is request of creation of remote database instance
 */
public class RemoteDatabaseRequest {

	@Nullable
	private final TemplateId templateId;
	@Getter
	private final Class<DatabaseType> type;
	@Nullable
	private final String instanceName;

	/**
	 * Required argument constructor.
	 * @param type Given type of database.
     * @param templateId Given id of template (Optional).
     * @param instanceName Given name of instance (Optional).
	 */
	public RemoteDatabaseRequest(Class<DatabaseType> type,
                                 @Nullable TemplateId templateId,
								 @Nullable String instanceName) {
		this.templateId = templateId;
		this.type = type;
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
