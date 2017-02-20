package contract.domain;

import lombok.ToString;

@ToString
public class TemplateId {

	private final long id;

	public TemplateId(long id) {
		this.id = id;
	}
}