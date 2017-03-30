package pl.gov.coi.cascades.server.persistance.hibernate.mapper;

import org.junit.Test;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateId;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 30.03.17.
 */
public class TemplateIdMapperTest {

    @Test
    public void testToHibernateEntity() throws Exception {
        // given
        TemplateIdMapper templateIdMapper = new TemplateIdMapper();
        String id = "stub";
        pl.gov.coi.cascades.contract.domain.TemplateId templateId = new pl.gov.coi.cascades.contract.domain.TemplateId(id);

        // when
        TemplateId actual = templateIdMapper.toHibernateEntity(templateId);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getTemplateOfId()).isEqualTo(id);
    }

    @Test
    public void testFromHibernateEntity() throws Exception {
        // given
        TemplateIdMapper templateIdMapper = new TemplateIdMapper();
        TemplateId templateId = new TemplateId();
        String id = "stub";
        templateId.setTemplateOfId(id);

        // when
        pl.gov.coi.cascades.contract.domain.TemplateId actual = templateIdMapper.fromHibernateEntity(templateId);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(id);
    }

}
