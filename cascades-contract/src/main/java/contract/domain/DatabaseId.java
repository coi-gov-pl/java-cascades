package contract.domain;

import lombok.ToString;

@ToString
public class DatabaseId {

	private final long id;

	public DatabaseId(long id) {
		this.id = id;
	}
}