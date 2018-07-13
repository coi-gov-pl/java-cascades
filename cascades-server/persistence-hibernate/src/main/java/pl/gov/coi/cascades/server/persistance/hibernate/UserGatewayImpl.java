package pl.gov.coi.cascades.server.persistance.hibernate;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gov.coi.cascades.server.domain.UserGateway;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;
import pl.gov.coi.cascades.server.persistance.hibernate.mapper.UserMapper;
import pl.wavesoftware.eid.exceptions.Eid;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 29.03.17.
 */
@Transactional
@Singleton
public class UserGatewayImpl implements UserGateway {

    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(UserGatewayImpl.class);
    private final Logger logger;
    private static final String USER_NAME_FIELD = "userName";
    private EntityManager entityManager;
    private final UserMapper userMapper;

    public UserGatewayImpl(UserMapper userMapper) {
        this(userMapper, DEFAULT_LOGGER);
    }

    @VisibleForTesting
    UserGatewayImpl(UserMapper userMapper,
                    Logger logger) {
        this.userMapper = userMapper;
        this.logger = logger;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<pl.gov.coi.cascades.server.domain.User> find(@Nullable String userName) {
        try {
            TypedQuery<User> query =
                entityManager.createQuery(
                    "SELECT user FROM User user " +
                        "WHERE user.username = :userName",
                    User.class
                )
                    .setParameter(USER_NAME_FIELD, userName)
                    .setMaxResults(1);

            return Optional.of(userMapper.fromHibernateEntity(query.getSingleResult()));
        } catch (NoResultException e) {
            logger.error(new Eid("20170329:171038")
                .makeLogMessage(
                    "Given name of user: %s hasn't been found: %s.",
                    userName,
                    e
                )
            );
            return Optional.empty();
        }
    }

    @Override
    public void save(@Nonnull pl.gov.coi.cascades.server.domain.User user) {
        User hibernateUser = userMapper.toHibernateEntity(user);
        entityManager.merge(hibernateUser);
    }
}
