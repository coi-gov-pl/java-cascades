package pl.gov.coi.cascades.server;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author <a href="mailto:mariusz.wyszomierski@coi.gov.pl">Mariusz Wyszomierski</a>
 */
public class DriverManagerDataSourceProviderImpl implements DriverManagerDataSourceProvider {

    @Override
    public DriverManagerDataSource produce() {
        return new DriverManagerDataSource();
    }
}
