package pl.gov.coi.cascades.server.persistance.stub;

import org.junit.Test;
import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.contract.domain.TemplateIdStatus;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.04.17.
 */
public class TemplateIdGatewayStubTest {

    @Test
    public void testFindWhenTemplateIdIsPresent() throws Exception {
        // given
        TemplateIdGatewayStub templateIdGatewayStub = new TemplateIdGatewayStub();

        // when
        Optional<TemplateId> actual = templateIdGatewayStub.find(TemplateIdGatewayStub.TEMPLATE_ID2.getId());

        // then
        assertThat(actual).isNotEqualTo(Optional.empty());
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(TemplateIdGatewayStub.TEMPLATE_ID2);
    }

    @Test
    public void testGetDefaultTemplateId() throws Exception {
        // given
        String id = "templateId3";
        TemplateIdStatus status = TemplateIdStatus.CREATED;
        String serverId = "mjtjbyu968y96";
        TemplateIdGatewayStub templateIdGatewayStub = new TemplateIdGatewayStub();

        // when
        Optional<TemplateId> actual = templateIdGatewayStub.getDefaultTemplateId();

        // then
        assertThat(actual).isNotEqualTo(Optional.empty());
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get().getId()).isEqualTo(id);
        assertThat(actual.get().getStatus()).isEqualTo(status);
        assertThat(actual.get().isDefault()).isTrue();
        assertThat(actual.get().getServerId()).isEqualTo(serverId);
    }

    @Test
    public void testGetAllTemplates() throws Exception {
        // given
        int NUMBER_OF_TEMPLATES = 4;
        TemplateIdGatewayStub templateIdGatewayStub = new TemplateIdGatewayStub();

        // when
        Map<String, TemplateId> actual = templateIdGatewayStub.getAllTemplates();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(NUMBER_OF_TEMPLATES);
        assertThat(actual).containsValues(
            TemplateIdGatewayStub.TEMPLATE_ID1,
            TemplateIdGatewayStub.TEMPLATE_ID2,
            TemplateIdGatewayStub.TEMPLATE_ID3,
            TemplateIdGatewayStub.TEMPLATE_ID4
        );
    }

    @Test
    public void testSetTemplate() throws Exception {
        // given
        int NUMBER_OF_TEMPLATES = 5;
        TemplateId TEMPLATE_ID = new TemplateId(
            "postgres_template",
            TemplateIdStatus.CREATED,
            false,
            "aaa123456789"
        );
        TemplateIdGatewayStub templateIdGatewayStub = new TemplateIdGatewayStub();

        // when
        templateIdGatewayStub.setTemplate(TEMPLATE_ID);
        Map<String, TemplateId> actual = templateIdGatewayStub.getAllTemplates();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(NUMBER_OF_TEMPLATES);
        assertThat(actual).containsValues(
            TemplateIdGatewayStub.TEMPLATE_ID1,
            TemplateIdGatewayStub.TEMPLATE_ID2,
            TemplateIdGatewayStub.TEMPLATE_ID3,
            TemplateIdGatewayStub.TEMPLATE_ID4,
            TEMPLATE_ID
        );
    }

    @Test
    public void testGetTemplate() throws Exception {
        // given
        TemplateIdGatewayStub templateIdGatewayStub = new TemplateIdGatewayStub();

        // when
        TemplateId actual = templateIdGatewayStub.getTemplate(TemplateIdGatewayStub.TEMPLATE_ID1.getId());

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(TemplateIdGatewayStub.TEMPLATE_ID1.getId());
        assertThat(actual.getServerId()).isEqualTo(TemplateIdGatewayStub.TEMPLATE_ID1.getServerId());
        assertThat(actual.getStatus()).isEqualTo(TemplateIdGatewayStub.TEMPLATE_ID1.getStatus());
    }

    @Test
    public void testClearTemplates() throws Exception {
        // given
        int NUMBER_OF_TEMPLATES = 0;
        TemplateIdGatewayStub templateIdGatewayStub = new TemplateIdGatewayStub();

        // when
        templateIdGatewayStub.clearTemplates();
        Map<String, TemplateId> actual = templateIdGatewayStub.getAllTemplates();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(NUMBER_OF_TEMPLATES);
    }

    @Test
    public void testRemoveTemplate() throws Exception {
        // given
        int NUMBER_OF_TEMPLATES = 3;
        TemplateIdGatewayStub templateIdGatewayStub = new TemplateIdGatewayStub();

        // when
        templateIdGatewayStub.removeTemplate(TemplateIdGatewayStub.TEMPLATE_ID1.getId());
        Map<String, TemplateId> actual = templateIdGatewayStub.getAllTemplates();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(NUMBER_OF_TEMPLATES);
        assertThat(actual).doesNotContainValue(TemplateIdGatewayStub.TEMPLATE_ID1);
    }

}
