package pl.gov.coi.cascades.junit4;

import pl.gov.coi.cascades.client.plugin.driver.Driver;
import pl.gov.coi.cascades.client.plugin.migration.Migration;
import pl.gov.coi.cascades.client.plugin.server.Server;

public class CascadesRuleBuilder {

	/**
	 * @param name
	 */
	public CascadesRuleBuilder instanceName(String name) {
		return null;
	}

	/**
	 * @param isReused
	 */
	public CascadesRuleBuilder tryToReuse(boolean isReused) {
		return null;
	}

	/**
	 * @param server
	 */
	public CascadesRuleBuilder reconfigure(Server server) {
		return null;
	}

	/**
	 * @param migration
	 */
	public CascadesRuleBuilder runMigration(Migration migration) {
		return null;
	}

	/**
	 * @param driver
	 */
	public CascadesRuleBuilder driver(Driver driver) {
		return null;
	}

	public CascadesRuleFactory build() {
		return null;
	}

}