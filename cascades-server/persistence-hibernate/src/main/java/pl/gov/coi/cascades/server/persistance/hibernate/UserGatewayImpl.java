package pl.gov.coi.cascades.server.persistance.hibernate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gov.coi.cascades.server.domain.DatabaseTypeClassNameService;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.UserGateway;
import pl.gov.coi.cascades.server.persistance.hibernate.mapper.UserMapper;
import pl.wavesoftware.eid.exceptions.Eid;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 29.03.17.
 */
public class UserGatewayImpl implements UserGateway {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserGatewayImpl.class);
    private static final String USER_NAME_FIELD = "userName";
    private EntityManager entityManager;
    private final UserMapper userMapper;

    @Inject
    public UserGatewayImpl(DatabaseTypeClassNameService databaseTypeClassNameService) {
        this.userMapper = new UserMapper(databaseTypeClassNameService);
    }

    @PersistenceContext
    void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<User> find(@Nullable String userName) {
        try {
            TypedQuery<pl.gov.coi.cascades.server.persistance.hibernate.entity.User> query =
                entityManager.createQuery(
                    "SELECT user FROM pl.gov.coi.cascades.server.persistance.hibernate.entity.User user " +
                        "WHERE user.username = :userName",
                    pl.gov.coi.cascades.server.persistance.hibernate.entity.User.class
                )
                .setParameter(USER_NAME_FIELD, userName)
                .setMaxResults(1);

            return Optional.of(userMapper.fromHibernateEntity(query.getSingleResult()));
        } catch (NoResultException e) {
            LOGGER.error(new Eid("20170329:171038")
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
    public void save(@Nonnull User user) {
        pl.gov.coi.cascades.server.persistance.hibernate.entity.User hibernateUser = userMapper.toHibernateEntity(user);
        entityManager.persist(hibernateUser);
    }

}
