package pl.gov.coi.cascades.contract.service;

import com.google.common.base.Optional;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.TemplateId;
import lombok.Getter;

import javax.annotation.Nullable;

public class RemoteDatabaseRequest {

	@Getter
	private final TemplateId id;
	@Getter
	private final Class<DatabaseType> type;
	private final String instanceName;

	/**
	 * Required argument constructor.
	 * @param id Given id of template.
	 * @param type Given type of database.
	 * @param instanceName Given name of instance.
	 */
	public RemoteDatabaseRequest(TemplateId id,
								 Class<DatabaseType> type,
								 @Nullable String instanceName) {
		this.id = id;
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
}