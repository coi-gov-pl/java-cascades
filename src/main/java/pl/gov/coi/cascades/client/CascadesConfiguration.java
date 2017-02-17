package pl.gov.coi.cascades.client;

import com.google.common.collect.ImmutableList;
import pl.gov.coi.cascades.client.plugin.driver.Driver;
import pl.gov.coi.cascades.client.plugin.driver.Oracle12MultitenantFactory;
import pl.gov.coi.cascades.client.plugin.migration.FlywayMigrationFactory;
import pl.gov.coi.cascades.client.plugin.migration.Migration;
import pl.gov.coi.cascades.client.plugin.server.JbossEapServerFactory;
import pl.gov.coi.cascades.client.plugin.server.Server;
import pl.gov.coi.cascades.junit4.CascadesRuleFactory;
import pl.gov.coi.cascades.junit4.RuleFactory;

public class CascadesConfiguration {

	public Iterable<RuleFactory> configure() {
		Server jbossEapServerBuilder = new JbossEapServerFactory();
		Migration flywayMigration = new FlywayMigrationFactory();
		Driver oracle12Multitenant = new Oracle12MultitenantFactory();
		CascadesRuleFactory cascadesRule = new CascadesRuleFactory();
		RuleFactory ruleFactory = cascadesRule.builder()
				.instanceName("PESEL")
				.tryToReuse(true)
				.reconfigure(
						jbossEapServerBuilder.builder()
								.useArquillianDefinition("jboss-remote")
								.build()
				)
				.runMigration(
						flywayMigration.builder()
								.setPrefix("V")
								.setScheme("PESEL")
								.build()
				)
				.driver(
						oracle12Multitenant.builder()
								.fromSeed("e3bc566741102aef")
				)
                .build();
		return ImmutableList.of(ruleFactory);
	}

}