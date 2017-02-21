package pl.gov.coi.cascades.contract.configuration;

import pl.gov.coi.cascades.contract.domain.NetworkBind;

public interface ConfigurationBuilder {

	/**
	 * Method gives configuration builder for given server.
	 * @param server Given server.
	 * @return Builder of configuration.
	 */
	ConfigurationBuilder reconfigure(Server server);

	/**
	 * Method gives configuration builder for given migration.
	 * @param migration Given migration.
	 * @return Builder of configuration.
	 */
	ConfigurationBuilder runMigration(Migration migration);

	/**
	 * Method gives configuration builder for given driver.
	 * @param driver Given driver.
	 * @return Builder of configuration.
	 */
	ConfigurationBuilder driver(Driver driver);

	/**
	 * Method gives configuration.
	 * @return Configuration.
	 */
	Configuration build();

	/**
	 * Method gives configuration builder for given name of instance. The name of instance is optional.
	 * @param name A name of instance.
	 * @return Builder of configuration.
	 */
	ConfigurationBuilder instanceName(String name);

	/**
	 * Method gives configuration builder for given information if database is reused. The default value is set to true.
	 * @param isReused Information if database is reused.
	 * @return Builder of configuration.
	 */
	ConfigurationBuilder tryToReuse(boolean isReused);

	/**
	 * Method gives configuration builder for given network bind.
	 * @param bind Given network bind.
	 * @return Builder of configuration.
	 */
	ConfigurationBuilder cascadesServer(NetworkBind bind);

}