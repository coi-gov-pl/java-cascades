package pl.gov.coi.cascades.contract;

import pl.gov.coi.cascades.contract.service.RemoteDatabaseSpec;

public interface Cascades {

	/**
	 * Method creates database.
	 */
	void createDatabase();

	/**
	 * Method removes database.
	 */
	void removeDatabase();

	/**
	 * Method gives information if database was created.
	 * @return Information if database was created.
	 */
	boolean isCreated();

	/**
	 * Method gives specification of remote database.
	 * @return Specification of remote database.
	 */
	RemoteDatabaseSpec getSpec();

}