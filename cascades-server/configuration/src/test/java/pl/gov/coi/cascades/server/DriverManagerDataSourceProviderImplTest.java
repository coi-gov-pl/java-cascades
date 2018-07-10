package pl.gov.coi.cascades.server;

import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import static org.assertj.core.api.Assertions.assertThat;

public class DriverManagerDataSourceProviderImplTest {

    private DriverManagerDataSourceProviderImpl provider = new DriverManagerDataSourceProviderImpl();

    @Test
    public void produce() {
        //when
        DriverManagerDataSource result = provider.produce();

        //then
        assertThat(result).isNotNull();
    }
}
