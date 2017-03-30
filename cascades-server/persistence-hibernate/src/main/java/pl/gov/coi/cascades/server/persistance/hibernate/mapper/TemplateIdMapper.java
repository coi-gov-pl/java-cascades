package pl.gov.coi.cascades.server.persistance.hibernate.mapper;

import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateId;

import static pl.wavesoftware.eid.utils.EidPreconditions.checkNotNull;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 30.03.17.
 */
public class TemplateIdMapper implements Mapper<TemplateId, pl.gov.coi.cascades.contract.domain.TemplateId> {

    @Override
    public TemplateId toHibernateEntity(pl.gov.coi.cascades.contract.domain.TemplateId templateId) {
        checkNotNull(templateId.getId(), "20170330:090230");

        TemplateId hibernateTemplateId = new TemplateId();
        hibernateTemplateId.setTemplateOfId(templateId.getId());
        return hibernateTemplateId;
    }

    @Override
    public pl.gov.coi.cascades.contract.domain.TemplateId fromHibernateEntity(TemplateId templateId) {
        checkNotNull(templateId.getTemplateOfId(), "20170330:090442");

        return new pl.gov.coi.cascades.contract.domain.TemplateId(templateId.getTemplateOfId());
    }

}
