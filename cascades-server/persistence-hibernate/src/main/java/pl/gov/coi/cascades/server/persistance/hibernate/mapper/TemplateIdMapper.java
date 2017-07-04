package pl.gov.coi.cascades.server.persistance.hibernate.mapper;

import pl.gov.coi.cascades.server.domain.Mapper;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.Template;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateIdStatus;

import javax.annotation.Nonnull;

import static pl.wavesoftware.eid.utils.EidPreconditions.checkNotNull;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 30.03.17.
 */
public class TemplateIdMapper implements Mapper<Template, pl.gov.coi.cascades.contract.domain.Template> {

    @Override
    public Template toHibernateEntity(@Nonnull pl.gov.coi.cascades.contract.domain.Template template) {
        checkNotNull(template.getId(), "20170330:090230");
        checkNotNull(template.getServerId(), "20170403:231918");
        checkNotNull(template.getStatus(), "20170403:231935");
        checkNotNull(template.isDefault(), "20170403:231953");
        checkNotNull(template.getVersion(), "20170512:101356");
        checkNotNull(template.getName(), "20170629:090218");

        Template hibernateTemplate = new Template();
        TemplateIdStatus templateIdStatus = template.getStatus()
            .equals(pl.gov.coi.cascades.contract.domain.TemplateIdStatus.CREATED)
            ? TemplateIdStatus.CREATED
            : TemplateIdStatus.DELETED;

        hibernateTemplate.setGeneratedId(template.getId());
        hibernateTemplate.setName(template.getName());
        hibernateTemplate.setDefault(template.isDefault());
        hibernateTemplate.setServerId(template.getServerId());
        hibernateTemplate.setStatus(templateIdStatus);
        hibernateTemplate.setVersion(template.getVersion());
        return hibernateTemplate;
    }

    @Override
    public pl.gov.coi.cascades.contract.domain.Template fromHibernateEntity(@Nonnull Template template) {
        checkNotNull(template.getName(), "20170330:090442");
        checkNotNull(template.getServerId(), "20170403:232202");
        checkNotNull(template.getStatus(), "20170403:232205");
        checkNotNull(template.isDefault(), "20170403:232209");
        checkNotNull(template.getVersion(), "20170512:101501");
        checkNotNull(template.getGeneratedId(), "20170629:090855");

        pl.gov.coi.cascades.contract.domain.TemplateIdStatus templateIdStatus = template.getStatus()
            .equals(TemplateIdStatus.CREATED)
            ? pl.gov.coi.cascades.contract.domain.TemplateIdStatus.CREATED
            : pl.gov.coi.cascades.contract.domain.TemplateIdStatus.DELETED;

        return new pl.gov.coi.cascades.contract.domain.Template(
            template.getGeneratedId(),
            template.getName(),
            templateIdStatus,
            template.isDefault(),
            template.getServerId(),
            template.getVersion()
        );
    }

}
