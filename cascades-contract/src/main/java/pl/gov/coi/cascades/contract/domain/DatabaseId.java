package pl.gov.coi.cascades.contract.domain;

import lombok.ToString;

@ToString
public class DatabaseId {

	private final String id;

	/**
	 * Required argument constructor.
	 * @param id Given id of database.
	 */
	public DatabaseId(String id) {
		this.id = id;
	}
}