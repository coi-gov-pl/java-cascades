package pl.gov.coi.cascades.junit4;

import pl.gov.coi.cascades.client.configuration.CascadesServerConfiguration;
import pl.gov.coi.cascades.client.configuration.CascadesServerConfiguration.Timeout;
import pl.gov.coi.cascades.client.presentation.CascadesOperationsLogger;
import pl.gov.coi.cascades.contract.configuration.ConfigurationBuilder;
import pl.gov.coi.cascades.contract.configuration.Driver;
import pl.gov.coi.cascades.contract.configuration.Migration;
import pl.gov.coi.cascades.contract.configuration.Server;

import javax.inject.Inject;
import java.net.URI;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 29.03.17
 */
final class CascadesRuleBuilderImpl implements CascadesRuleBuilder {
    private final ConfigurationBuilder configurationBuilder;
    private final CascadesServerConfiguration cascadesServerConfiguration;
    private final CascadesOperationsLogger operationsLogger;

    @Inject
    CascadesRuleBuilderImpl(ConfigurationBuilder configurationBuilder,
                            CascadesServerConfiguration cascadesServerConfiguration,
                            CascadesOperationsLogger operationsLogger) {
        this.configurationBuilder = configurationBuilder;
        this.cascadesServerConfiguration = cascadesServerConfiguration;
        this.operationsLogger = operationsLogger;
    }

    @Override
    public CascadesRuleBuilder reconfigure(Server server) {
        configurationBuilder.reconfigure(server);
        return this;
    }

    @Override
    public CascadesRuleBuilder runMigration(Migration migration) {
        configurationBuilder.runMigration(migration);
        return this;
    }

    @Override
    public CascadesRuleBuilder driver(Driver driver) {
        configurationBuilder.driver(driver);
        return this;
    }

    @Override
    public CascadesRuleBuilder instanceName(String name) {
        configurationBuilder.instanceName(name);
        return this;
    }

    @Override
    public CascadesRuleBuilder tryToReuse(boolean tryToReuse) {
        configurationBuilder.tryToReuse(tryToReuse);
        return this;
    }

    @Override
    public CascadesRule build() {
        URI serverAddress = cascadesServerConfiguration.produceCascadesServerAddress();
        Timeout timeout = cascadesServerConfiguration.produceOperationsTimeout();
        configurationBuilder.cascadesServer(serverAddress);
        configurationBuilder.operationsTimeout(
            timeout.getNumber(), timeout.getTimeUnit()
        );
        return new CascadesRule(
            configurationBuilder.build(),
            operationsLogger
        );
    }
}
