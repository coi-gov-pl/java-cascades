package contract.configuration;

import contract.domain.NetworkBind;

public interface ConfigurationBuilder {

	/**
	 * 
	 * @param name
	 */
	ConfigurationBuilder instanceName(String name);

	/**
	 * 
	 * @param isReused
	 */
	ConfigurationBuilder tryToReuse(boolean isReused);

	/**
	 * 
	 * @param server
	 */
	ConfigurationBuilder reconfigure(Server server);

	/**
	 * 
	 * @param migration
	 */
	ConfigurationBuilder runMigration(Migration migration);

	/**
	 * 
	 * @param driver
	 */
	ConfigurationBuilder driver(Driver driver);

	Configuration build();

	/**
	 * 
	 * @param bind
	 */
	ConfigurationBuilder cascadesServer(NetworkBind bind);

}