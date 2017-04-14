package pl.gov.coi.cascades.server.persistance.hibernate.mapper;

import pl.gov.coi.cascades.server.domain.Mapper;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateId;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateIdStatus;

import static pl.wavesoftware.eid.utils.EidPreconditions.checkNotNull;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 30.03.17.
 */
public class TemplateIdMapper implements Mapper<TemplateId, pl.gov.coi.cascades.contract.domain.TemplateId> {

    private static final int BASE_36 = 36;

    @Override
    public TemplateId toHibernateEntity(pl.gov.coi.cascades.contract.domain.TemplateId templateId) {
        checkNotNull(templateId.getId(), "20170330:090230");
        checkNotNull(templateId.getServerId(), "20170403:231918");
        checkNotNull(templateId.getStatus(), "20170403:231935");
        checkNotNull(templateId.isDefault(), "20170403:231953");

        TemplateId hibernateTemplateId = new TemplateId();
        TemplateIdStatus templateIdStatus = templateId.getStatus()
            .equals(pl.gov.coi.cascades.contract.domain.TemplateIdStatus.CREATED)
            ? TemplateIdStatus.CREATED
            : TemplateIdStatus.DELETED;

        hibernateTemplateId.setId(Long.parseLong(templateId.getId(), BASE_36));
        hibernateTemplateId.setDefault(templateId.isDefault());
        hibernateTemplateId.setServerId(templateId.getServerId());
        hibernateTemplateId.setStatus(templateIdStatus);
        return hibernateTemplateId;
    }

    @Override
    public pl.gov.coi.cascades.contract.domain.TemplateId fromHibernateEntity(TemplateId templateId) {
        checkNotNull(templateId.getId(), "20170330:090442");
        checkNotNull(templateId.getServerId(), "20170403:232202");
        checkNotNull(templateId.getStatus(), "20170403:232205");
        checkNotNull(templateId.isDefault(), "20170403:232209");

        pl.gov.coi.cascades.contract.domain.TemplateIdStatus templateIdStatus = templateId.getStatus()
            .equals(TemplateIdStatus.CREATED)
            ? pl.gov.coi.cascades.contract.domain.TemplateIdStatus.CREATED
            : pl.gov.coi.cascades.contract.domain.TemplateIdStatus.DELETED;

        return new pl.gov.coi.cascades.contract.domain.TemplateId(
            Long.toString(templateId.getId(), BASE_36),
            templateIdStatus,
            templateId.isDefault(),
            templateId.getServerId()
        );
    }

}
