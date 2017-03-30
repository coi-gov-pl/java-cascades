package pl.gov.coi.cascades.client.configuration;

import lombok.Getter;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 29.03.17
 */
public interface CascadesServerConfiguration {
    /**
     * Produces a address for cascades server
     * @return a cascades server address
     */
    URI produceCascadesServerAddress();

    /**
     * Produce a remote operations timeout for cascades server
     * @return a timeout object
     */
    Timeout produceOperationsTimeout();

    /**
     * A timeout object
     */
    @Getter
    class Timeout {
        private final long number;
        private final TimeUnit timeUnit;

        public Timeout(long number, TimeUnit timeUnit) {
            this.number = number;
            this.timeUnit = timeUnit;
        }
    }
}
