package pl.gov.coi.cascades.server.persistance.hibernate;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.slf4j.Logger;
import pl.gov.coi.cascades.server.domain.DatabaseTypeClassNameService;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.persistance.hibernate.mapper.UserMapper;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 05.05.17.
 */
public class UserGatewayImplTest {

    @Mock
    private DatabaseTypeClassNameService databaseTypeClassNameService;

    @Mock
    private EntityManager entityManager;

    @Mock
    private UserMapper userMapper;

    @Mock
    private TypedQuery<Object> query;

    @Mock
    private Logger logger;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private UserGatewayImpl userGateway;

    @Before
    public void init() {
        userGateway = new UserGatewayImpl(
            userMapper
        );
    }

    @Test
    public void testSave() throws Exception {
        // given
        String username = "Kendrick Lamar";
        String id = "123456789";
        String email = "klamar@example.org";
        User user = new User(
            username,
            id,
            email
        );

        pl.gov.coi.cascades.server.persistance.hibernate.entity.User userEntity =
            new pl.gov.coi.cascades.server.persistance.hibernate.entity.User();
        userEntity.setUsername(username);
        userEntity.setId(Long.parseLong(id));
        userEntity.setEmail(email);

        when(userMapper.toHibernateEntity(user)).thenReturn(userEntity);
        userGateway.setEntityManager(entityManager);

        // when
        userGateway.save(user);

        // then
        verify(entityManager, times(1)).getReference(any(), anyLong());
        verify(entityManager, times(1)).persist(any());
    }

    @Test
    public void testFind() throws Exception {
        // given
        String username = "Kendrick Lamar";
        String id = "123456789";
        String email = "klamar@example.org";
        pl.gov.coi.cascades.server.persistance.hibernate.entity.User user =
            new pl.gov.coi.cascades.server.persistance.hibernate.entity.User();
        user.setUsername(username);
        user.setId(Long.parseLong(id));
        user.setEmail(email);

        User userDomain = new User(
            username,
            id,
            email
        );

        userGateway.setEntityManager(entityManager);
        when(entityManager.createQuery(anyString(), any())).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.setMaxResults(anyInt())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(user);
        when(userMapper.fromHibernateEntity(user)).thenReturn(userDomain);

        // when
        Optional<User> actual = userGateway.find(username);

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get().getUsername()).isEqualTo(username);
        assertThat(actual.get().getId()).isEqualTo(id);
        assertThat(actual.get().getEmail()).isEqualTo(email);
    }

    @Test
    public void testFindWhenExceptionOccurred() throws Exception {
        // given
        String username = "Kendrick Lamar";
        String id = "123456789";
        String email = "klamar@example.org";
        NoResultException exception = new NoResultException("There is no result.");

        pl.gov.coi.cascades.server.persistance.hibernate.entity.User user =
            new pl.gov.coi.cascades.server.persistance.hibernate.entity.User();
        user.setUsername(username);
        user.setId(Long.parseLong(id));
        user.setEmail(email);

        UserGatewayImpl userGateway = new UserGatewayImpl(
            new UserMapper(databaseTypeClassNameService),
            logger
        );
        userGateway.setEntityManager(entityManager);
        when(entityManager.createQuery(anyString(), any())).thenThrow(exception);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.setMaxResults(anyInt())).thenReturn(query);

        // when
        Optional<User> actual = userGateway.find(username);

        // then
        assertThat(actual.isPresent()).isFalse();
        verify(logger, times(1)).error(contains("20170329:171038"));
    }
}
