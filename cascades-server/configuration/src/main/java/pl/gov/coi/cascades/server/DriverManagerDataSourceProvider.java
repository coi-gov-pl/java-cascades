package pl.gov.coi.cascades.server;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author <a href="mailto:mariusz.wyszomierski@coi.gov.pl">Mariusz Wyszomierski</a>
 */
public interface DriverManagerDataSourceProvider {

    /**
     * Provides instance of {@link DriverManagerDataSource}.
     * @return {@link DriverManagerDataSource}
     */
    DriverManagerDataSource produce();
}
