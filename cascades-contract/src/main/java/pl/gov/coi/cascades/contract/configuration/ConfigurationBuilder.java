package pl.gov.coi.cascades.contract.configuration;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * This is a builder interface for configuration
 *
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszyński</a>
 * @since 21.02.17
 */
public interface ConfigurationBuilder {

    /**
     * Method gives configuration builder for given server.
     *
     * @param server Given server.
     * @return Builder of configuration.
     */
    ConfigurationBuilder reconfigure(Server server);

    /**
     * Method gives configuration builder for given migration.
     *
     * @param migration Given migration.
     * @return Builder of configuration.
     */
    ConfigurationBuilder runMigration(Migration migration);

    /**
     * Method gives configuration builder for given driver.
     *
     * @param driver Given driver.
     * @return Builder of configuration.
     */
    ConfigurationBuilder driver(Driver driver);

    /**
     * Method gives configuration builder for given name of instance. The name of instance is optional.
     *
     * @param name A name of instance.
     * @return Builder of configuration.
     */
    ConfigurationBuilder instanceName(String name);

    /**
     * Method gives configuration builder for given information if database is reused.
     * The default value is set to true.
     *
     * @param tryToReuse Information if database is reused.
     * @return Builder of configuration.
     */
    ConfigurationBuilder tryToReuse(boolean tryToReuse);

    /**
     * Method gives configuration builder for given server address.
     *
     * @param serverAddress Given server address.
     * @return Builder of configuration.
     */
    ConfigurationBuilder cascadesServer(URI serverAddress);

    /**
     * Method sets timeout for Cascades operations
     * @param timeout a timeout in given time unit
     * @param timeUnit a time unit
     * @return Builder of configuration.
     */
    ConfigurationBuilder operationsTimeout(long timeout, TimeUnit timeUnit);

    /**
     * Method gives configuration.
     *
     * @return Configuration.
     */
    Configuration build();

}
