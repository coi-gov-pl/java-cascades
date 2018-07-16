package pl.gov.coi.cascades.server.persistance.hibernate;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseInstanceGateway;
import pl.gov.coi.cascades.server.persistance.hibernate.mapper.DatabaseInstanceMapper;;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
@Transactional
public class DatabaseInstanceGatewayImpl implements DatabaseInstanceGateway {

    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(DatabaseInstanceGatewayImpl.class);
    private Logger logger;
    private final DatabaseInstanceMapper databaseInstanceMapper;
    private EntityManager entityManager;


    public DatabaseInstanceGatewayImpl(DatabaseInstanceMapper databaseInstanceMapper) {
        this(
            databaseInstanceMapper,
            DEFAULT_LOGGER
        );
    }

    @VisibleForTesting
    DatabaseInstanceGatewayImpl(DatabaseInstanceMapper databaseInstanceMapper, Logger logger) {
        this.databaseInstanceMapper = databaseInstanceMapper;
        this.logger = logger;
    }

    @PersistenceContext
    void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public DatabaseInstance save(DatabaseInstance databaseInstance) {
        pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance databaseInstanceEntity
            = databaseInstanceMapper.toHibernateEntity(databaseInstance);
        entityManager.merge(databaseInstanceEntity);

        return databaseInstance;
    }

    @Override
    public void deleteDatabase(DatabaseInstance databaseInstance) {
        pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance databaseInstanceEntity
            = databaseInstanceMapper.toHibernateEntity(databaseInstance);
        entityManager.merge(databaseInstanceEntity);
    }

    @Override
    @Deprecated
    public String getRemoteServerId() {
        // TODO: write an implementation
        throw new UnsupportedOperationException("Not yet implemented!");
    }
}
