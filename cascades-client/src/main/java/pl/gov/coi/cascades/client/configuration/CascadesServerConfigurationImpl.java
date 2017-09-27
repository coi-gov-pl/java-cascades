package pl.gov.coi.cascades.client.configuration;

import com.google.common.base.Optional;

import javax.annotation.Nullable;
import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 29.03.17
 */
public class CascadesServerConfigurationImpl implements CascadesServerConfiguration {

    private static final String SYSTEM_PROPERTY_KEY = "cascades.server.address";
    private static final String ENVIRONMENT_VARIABLE_KEY = "CASCADES_SERVER_ADDRESS";
    private static final String DEFAULT_CASECADES_SERVER = "http://cascades.localdomain:8080/";
    private static final String TIMEOUT_PROPERTY_KEY = "cascades.server.timeout";
    private static final String CASCADES_TIMEOUT_KEY = "CASCADES_TIMEOUT";
    private static final String DEFAULT_CASCADES_TIMEOUT = "300000";

    @Override
    public URI produceCascadesServerAddress() {
        @Nullable String value = System.getProperty(
            SYSTEM_PROPERTY_KEY,
            System.getenv(ENVIRONMENT_VARIABLE_KEY)
        );
        String host = Optional.fromNullable(value).or(DEFAULT_CASECADES_SERVER);
        return URI.create(host);
    }

    @Override
    public Timeout produceOperationsTimeout() {
        @Nullable String value = System.getProperty(
            TIMEOUT_PROPERTY_KEY,
            System.getenv(CASCADES_TIMEOUT_KEY)
        );
        Long number = Long.parseLong(Optional.fromNullable(value).or(DEFAULT_CASCADES_TIMEOUT));
        return new Timeout(
            number,
            TimeUnit.MILLISECONDS
        );
    }
}
