package pl.gov.coi.cascades.server;

import java.util.Map;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 11.04.17.
 */
@FunctionalInterface
public interface OsgiFrameworkConfigurator {
    void configure(Map<String, String> config);
}
