package pl.gov.coi.cascades.client.configuration;

import org.junit.Test;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 03.07.17
 */
public class CascadesServerConfigurationImplTest {

    @Test
    public void testProduceCascadesServerAddress() throws Exception {
        // given
        CascadesServerConfigurationImpl serverConfiguration = new CascadesServerConfigurationImpl();

        // when
        URI uri = serverConfiguration.produceCascadesServerAddress();

        // then
        assertThat(uri).isNotNull();
        assertThat(uri.getAuthority()).isEqualTo("cascades.localdomain:8080");
    }

    @Test
    public void testProduceOperationsTimeout() throws Exception {
        // given
        long defaultTimeout = 300000;
        CascadesServerConfigurationImpl serverConfiguration = new CascadesServerConfigurationImpl();

        // when
        CascadesServerConfiguration.Timeout timeout = serverConfiguration.produceOperationsTimeout();

        // then
        assertThat(timeout).isNotNull();
        assertThat(timeout.getNumber()).isEqualTo(defaultTimeout);
        assertThat(timeout.getTimeUnit()).isEqualByComparingTo(TimeUnit.MILLISECONDS);
    }

}
