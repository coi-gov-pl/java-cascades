package contract.service;

import com.google.common.base.Optional;
import com.sun.istack.internal.Nullable;
import contract.domain.DatabaseType;
import contract.domain.TemplateId;
import lombok.Getter;

@Getter
public class RemoteDatabaseRequest {

	private final TemplateId id;
	private final Class<DatabaseType> type;
	private final Optional<String> instanceName;

	public RemoteDatabaseRequest(TemplateId id,
								 Class<DatabaseType> type,
								 @Nullable String instanceName) {
		this.id = id;
		this.type = type;
		this.instanceName = Optional.fromNullable(instanceName);
	}
}