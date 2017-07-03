package pl.gov.coi.cascades.client.configuration;

import org.junit.Test;

import java.net.URI;

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

}
