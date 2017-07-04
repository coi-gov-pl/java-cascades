package pl.gov.coi.cascades.server.persistance.hibernate;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.Template;
import pl.gov.coi.cascades.server.persistance.hibernate.mapper.TemplateIdMapper;
import pl.wavesoftware.eid.exceptions.Eid;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 30.03.17.
 */
public class TemplateIdGatewayImpl implements TemplateIdGateway {

    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(TemplateIdGatewayImpl.class);
    private static final TemplateIdMapper DEFAULT_TEMPLATE_ID_MAPPER = new TemplateIdMapper();
    private static final String TEMPLATE_ID_FIELD = "templateIdAsLong";
    private static final int RADIX_36 = 36;
    private EntityManager entityManager;
    private Logger logger;
    private final TemplateIdMapper templateIdMapper;

    public TemplateIdGatewayImpl() {
        this(
            DEFAULT_TEMPLATE_ID_MAPPER,
            DEFAULT_LOGGER
        );
    }

    @VisibleForTesting
    public TemplateIdGatewayImpl(TemplateIdMapper templateIdMapper,
                          Logger logger) {
        this.templateIdMapper = templateIdMapper;
        this.logger = logger;
    }

    @PersistenceContext
    void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<pl.gov.coi.cascades.contract.domain.Template> find(@Nullable String templateId) {
        try {
            Long templateIdAsLong = templateId != null
                ? Long.parseLong(templateId, RADIX_36)
                : null;
            TypedQuery<Template> query =
                entityManager.createQuery(
                    "SELECT template FROM Template template " +
                        "WHERE template.id = :templateIdAsLong",
                    Template.class
                )
                .setParameter(TEMPLATE_ID_FIELD, templateIdAsLong)
                .setMaxResults(1);

            return Optional.of(templateIdMapper.fromHibernateEntity(query.getSingleResult()));
        } catch (NoResultException e) {
            logger.error(new Eid("20170330:092228")
                .makeLogMessage(
                    "Given id of template: %s hasn't been found: %s.",
                    templateId,
                    e
                )
            );
            return Optional.empty();
        }
    }

    @Override
    public Optional<pl.gov.coi.cascades.contract.domain.Template> getDefaultTemplateId() {
        try {
            TypedQuery<Template> query =
                entityManager.createQuery(
                    "SELECT template FROM Template template " +
                        "WHERE template.isDefault = true",
                    Template.class
                )
                .setMaxResults(1);

            return Optional.of(templateIdMapper.fromHibernateEntity(query.getSingleResult()));
        } catch (NoResultException e) {
            logger.error(new Eid("20170406:092655")
                .makeLogMessage(
                    "No default template id has been found",
                    e
                )
            );
            return Optional.empty();
        }
    }

    @Override
    public void addTemplate(pl.gov.coi.cascades.contract.domain.Template template) {
        if (logger.isInfoEnabled()) {
            logger.info(new Eid("20170626:140337")
                .makeLogMessage(
                    "Given templateId has been saved."
                )
            );
        }
    }

}
