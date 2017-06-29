package pl.gov.coi.cascades.server.presentation.launchdatabase;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.04.17.
 */
public class RemoteDatabaseRequestDTOTest {

    @Test
    public void testParameterConstructorWhenTemplateIdIsNotPresent() {
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

    @Test
    public void testParameterConstructorWhenTemplateIdIsPresent() {
        // given
        String type = "";
        String instanceName = "oracler49903";
        String templateId = "oracle_template";

        // when
        RemoteDatabaseRequestDTO actual = new RemoteDatabaseRequestDTO(
            type,
            templateId,
            instanceName
        );

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getTemplateId().isPresent()).isTrue();
        assertThat(actual.getTemplateId().get().getId()).isEqualTo(templateId);
        assertThat(actual.getTemplateId().get()).isInstanceOf(InputTemplate.class);
    }

}
