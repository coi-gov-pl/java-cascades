package pl.gov.coi.cascades.contract;

public interface CascadesRestEndpoint {

	void launchDatabase();

	/**
	 * 
	 * @param databaseName
	 */
	void deleteDatabase(String databaseName);

}