package pl.gov.coi.cascades.server.persistance.hibernate.mapper;

import pl.gov.coi.cascades.server.domain.Mapper;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateId;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateIdStatus;

import javax.annotation.Nonnull;

import static pl.wavesoftware.eid.utils.EidPreconditions.checkNotNull;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 30.03.17.
 */
public class TemplateIdMapper implements Mapper<TemplateId, pl.gov.coi.cascades.contract.domain.TemplateId> {

    @Override
    public TemplateId toHibernateEntity(@Nonnull pl.gov.coi.cascades.contract.domain.TemplateId templateId) {
        checkNotNull(templateId.getId(), "20170330:090230");
        checkNotNull(templateId.getServerId(), "20170403:231918");
        checkNotNull(templateId.getStatus(), "20170403:231935");
        checkNotNull(templateId.isDefault(), "20170403:231953");
        checkNotNull(templateId.getVersion(), "20170512:101356");

        TemplateId hibernateTemplateId = new TemplateId();
        TemplateIdStatus templateIdStatus = templateId.getStatus()
            .equals(pl.gov.coi.cascades.contract.domain.TemplateIdStatus.CREATED)
            ? TemplateIdStatus.CREATED
            : TemplateIdStatus.DELETED;

        hibernateTemplateId.setName(templateId.getId());
        hibernateTemplateId.setDefault(templateId.isDefault());
        hibernateTemplateId.setServerId(templateId.getServerId());
        hibernateTemplateId.setStatus(templateIdStatus);
        hibernateTemplateId.setVersion(templateId.getVersion());
        return hibernateTemplateId;
    }

    @Override
    public pl.gov.coi.cascades.contract.domain.TemplateId fromHibernateEntity(@Nonnull TemplateId templateId) {
        checkNotNull(templateId.getName(), "20170330:090442");
        checkNotNull(templateId.getServerId(), "20170403:232202");
        checkNotNull(templateId.getStatus(), "20170403:232205");
        checkNotNull(templateId.isDefault(), "20170403:232209");
        checkNotNull(templateId.getVersion(), "20170512:101501");

        pl.gov.coi.cascades.contract.domain.TemplateIdStatus templateIdStatus = templateId.getStatus()
            .equals(TemplateIdStatus.CREATED)
            ? pl.gov.coi.cascades.contract.domain.TemplateIdStatus.CREATED
            : pl.gov.coi.cascades.contract.domain.TemplateIdStatus.DELETED;

        return new pl.gov.coi.cascades.contract.domain.TemplateId(
            templateId.getName(),
            templateIdStatus,
            templateId.isDefault(),
            templateId.getServerId(),
            templateId.getVersion()
        );
    }

}
