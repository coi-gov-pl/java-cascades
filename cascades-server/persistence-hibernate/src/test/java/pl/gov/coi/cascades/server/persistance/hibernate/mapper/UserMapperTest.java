package pl.gov.coi.cascades.server.persistance.hibernate.mapper;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.server.domain.DatabaseTypeClassNameService;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.03.17.
 */
public class UserMapperTest {

    private String email = "jrambo@example.org";
    private String username = "jrambo";
    private String id = "12345678";

    @Mock
    private DatabaseTypeClassNameService databaseTypeClassNameService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testToHibernateEntity() throws Exception {
        // given
        UserMapper userMapper = new UserMapper();
        pl.gov.coi.cascades.server.domain.User user = new pl.gov.coi.cascades.server.domain.User(
            username,
            id,
            email
        );

        // when
        User actual = userMapper.toHibernateEntity(user);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getUsername()).isEqualTo(username);
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getId()).isEqualTo(Long.parseLong(id));
    }

    @Test
    public void testFromHibernateEntity() throws Exception {
        // given
        UserMapper userMapper = new UserMapper();
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setId(Long.parseLong(id));

        // when
        pl.gov.coi.cascades.server.domain.User actual = userMapper.fromHibernateEntity(user);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getUsername()).isEqualTo(username);
    }

}
