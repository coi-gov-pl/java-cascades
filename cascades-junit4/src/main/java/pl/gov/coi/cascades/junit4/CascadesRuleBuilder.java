package pl.gov.coi.cascades.junit4;

import pl.gov.coi.cascades.contract.configuration.Driver;
import pl.gov.coi.cascades.contract.configuration.Migration;
import pl.gov.coi.cascades.contract.configuration.Server;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 29.03.17
 */
public interface CascadesRuleBuilder {
    /**
     * Method gives configuration builder for given server.
     *
     * @param server Given server.
     * @return Builder of cascades rule.
     */
    CascadesRuleBuilder reconfigure(Server server);

    /**
     * Method gives configuration builder for given migration.
     *
     * @param migration Given migration.
     * @return Builder of cascades rule.
     */
    CascadesRuleBuilder runMigration(Migration migration);

    /**
     * Method gives configuration builder for given driver.
     *
     * @param driver Given driver.
     * @return Builder of cascades rule.
     */
    CascadesRuleBuilder driver(Driver driver);

    /**
     * Method gives configuration builder for given name of instance.
     * The name of instance is optional.
     *
     * @param name A name of instance.
     * @return Builder of cascades rule.
     */
    CascadesRuleBuilder instanceName(String name);

    /**
     * Method gives configuration builder for given information if database is reused.
     * The default value is set to true.
     *
     * @param tryToReuse Information if database is reused.
     * @return Builder of cascades rule.
     */
    CascadesRuleBuilder tryToReuse(boolean tryToReuse);

    /**
     * Method gives a junit rule.
     *
     * @return a junit rule.
     */
    CascadesRule build();
}
