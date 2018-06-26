package pl.gov.coi.cascades.server.persistance.hibernate;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.server.domain.DatabaseIdGateway;
import pl.gov.coi.cascades.server.domain.DatabaseTypeClassNameService;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance;
import pl.gov.coi.cascades.server.persistance.hibernate.mapper.DatabaseInstanceMapper;
import pl.wavesoftware.eid.exceptions.Eid;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 02.04.17.
 */
@Transactional
public class DatabaseIdGatewayImpl implements DatabaseIdGateway {

    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(DatabaseIdGatewayImpl.class);
    private static final String DATABASE_ID_FIELD = "databaseIdAsLong";
    private static final int RADIX_36 = 36;
    private EntityManager entityManager;
    private Logger logger;
    private final DatabaseInstanceMapper databaseInstanceMapper;

    public DatabaseIdGatewayImpl(DatabaseInstanceMapper databaseInstanceMapper) {
        this(
            databaseInstanceMapper,
            DEFAULT_LOGGER
        );
    }

    @VisibleForTesting
    DatabaseIdGatewayImpl(DatabaseInstanceMapper databaseInstanceMapper,
                          Logger logger) {
        this.databaseInstanceMapper = databaseInstanceMapper;
        this.logger = logger;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<pl.gov.coi.cascades.server.domain.DatabaseInstance> findInstance(@Nullable DatabaseId databaseId) {
        try {
            Long databaseIdAsLong = databaseId != null
                ? Long.parseLong(databaseId.getId(), RADIX_36)
                : null;
            TypedQuery<DatabaseInstance> query =
                entityManager.createQuery(
                    "SELECT instance FROM DatabaseInstance instance " +
                        "WHERE instance.id = :databaseIdAsLong",
                    DatabaseInstance.class
                )
                .setParameter(DATABASE_ID_FIELD, databaseIdAsLong)
                .setMaxResults(1);

            return Optional.of(databaseInstanceMapper.fromHibernateEntity(query.getSingleResult()));
        } catch (NoResultException e) {
            logger.error(new Eid("20170402:222713")
                .makeLogMessage(
                    "Given id of database: %s hasn't been found: %s.",
                    databaseId,
                    e
                )
            );
            return Optional.empty();
        }
    }

}
