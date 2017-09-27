package pl.gov.coi.cascades.server;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.07.17
 */
public class ServerConfigurationServiceTest {

    @Test
    public void testGetManagedServers() throws Exception {
        // given
        ServerConfigurationService serverConfigurationService = new ServerConfigurationService();

        // when
        List<ServerDef> actual = serverConfigurationService.getManagedServers();

        // then
        assertThat(actual).isNotNull();
    }

}
