package pl.gov.coi.cascades.server.presentation.launchdatabase;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.04.17.
 */
public class RemoteDatabaseRequestDTOTest {

    @Test
    public void testParameterConstructor() {
        // given
        String type = "";
        String instanceName = "oracler49903";

        // when
        RemoteDatabaseRequestDTO actual = new RemoteDatabaseRequestDTO(
            type,
            null,
            instanceName
        );

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getTemplateId()).isEqualTo(com.google.common.base.Optional.absent());
    }

}
