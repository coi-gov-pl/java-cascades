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
        assertThat(actual.getTemplate()).isEqualTo(com.google.common.base.Optional.absent());
    }

    @Test
    public void testParameterConstructorWhenTemplateIdIsPresent() {
        // given
        String type = "";
        String instanceName = "oracler49903";
        String templateName = "oracle_template";

        // when
        RemoteDatabaseRequestDTO actual = new RemoteDatabaseRequestDTO(
            type,
            templateName,
            instanceName
        );

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getTemplate().isPresent()).isTrue();
        assertThat(actual.getTemplate().get().getId()).isEqualTo(templateName);
        assertThat(actual.getTemplate().get()).isInstanceOf(InputTemplate.class);
    }

}
