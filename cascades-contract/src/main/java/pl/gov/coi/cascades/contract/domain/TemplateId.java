package pl.gov.coi.cascades.contract.domain;

import lombok.ToString;

@ToString
public class TemplateId {

	private final String id;

	/**
	 * Required argument constructor.
	 * @param id Given id of template.
	 */
	public TemplateId(String id) {
		this.id = id;
	}
}