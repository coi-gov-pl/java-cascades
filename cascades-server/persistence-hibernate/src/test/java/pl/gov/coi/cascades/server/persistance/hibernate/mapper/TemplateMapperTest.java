package pl.gov.coi.cascades.server.persistance.hibernate.mapper;

import org.junit.Test;
import pl.gov.coi.cascades.contract.domain.TemplateIdStatus;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.Template;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 30.03.17.
 */
public class TemplateMapperTest {

    @Test
    public void testToHibernateEntityWhenTemplateIdIsDeleted() throws Exception {
        // given
        TemplateIdMapper templateIdMapper = new TemplateIdMapper();
        String id = "58893453";
        String name = "oracle_template";
        String serverId = "gta73284";
        String version = "0.0.1";
        pl.gov.coi.cascades.contract.domain.Template template = new pl.gov.coi.cascades.contract.domain.Template(
            id,
            name,
            TemplateIdStatus.DELETED,
            true,
            serverId,
            version
        );

        // when
        Template actual = templateIdMapper.toHibernateEntity(template);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.getGeneratedId()).isEqualTo(id);
        assertThat(actual.isDefault()).isTrue();
        assertThat(actual.getServerId()).isEqualTo(serverId);
        assertThat(actual.getStatus().name()).isEqualTo(TemplateIdStatus.DELETED.name());
    }

    @Test
    public void testToHibernateEntity() throws Exception {
        // given
        TemplateIdMapper templateIdMapper = new TemplateIdMapper();
        String id = "673735756";
        String serverId = "fre5345";
        String version = "0.0.1";
        String name = "oracle_template";
        pl.gov.coi.cascades.contract.domain.Template template = new pl.gov.coi.cascades.contract.domain.Template(
            id,
            name,
            TemplateIdStatus.CREATED,
            true,
            serverId,
            version
        );

        // when
        Template actual = templateIdMapper.toHibernateEntity(template);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getGeneratedId()).isEqualTo(id);
        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.isDefault()).isTrue();
        assertThat(actual.getServerId()).isEqualTo(serverId);
        assertThat(actual.getStatus().name()).isEqualTo(TemplateIdStatus.CREATED.name());
        assertThat(actual.getVersion()).isEqualTo(version);
    }

    @Test
    public void testFromHibernateEntity() throws Exception {
        // given
        TemplateIdMapper templateIdMapper = new TemplateIdMapper();
        Template template = new Template();
        String id = "dg6jf2g7";
        String serverId = "fre5345";
        String version = "0.0.1";
        String name = "oracle_template";
        template.setName(name);
        template.setGeneratedId(id);
        template.setDefault(false);
        template.setServerId(serverId);
        template.setVersion(version);
        template.setStatus(pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateIdStatus.DELETED);

        // when
        pl.gov.coi.cascades.contract.domain.Template actual = templateIdMapper.fromHibernateEntity(
            template
        );

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.isDefault()).isFalse();
        assertThat(actual.getServerId()).isEqualTo(serverId);
        assertThat(actual.getVersion()).isEqualTo(version);
        assertThat(actual.getStatus().name()).isEqualTo(TemplateIdStatus.DELETED.name());
    }

}
