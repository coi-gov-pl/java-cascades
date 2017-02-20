package contract.configuration;

import com.google.common.base.Optional;
import com.sun.istack.internal.Nullable;
import contract.domain.DatabaseType;
import contract.domain.TemplateId;
import lombok.Getter;

@Getter
public class Driver {

	private final Optional<TemplateId> templateId;
	private final DatabaseType type;

	public Driver(@Nullable TemplateId templateId,
				  DatabaseType type) {
		this.templateId = Optional.fromNullable(templateId);
		this.type = type;
	}

}