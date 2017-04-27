package pl.gov.coi.cascades.server.persistance.stub;

import org.junit.Before;
import org.junit.Test;
import pl.gov.coi.cascades.server.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.04.17.
 */
public class UserGatewayStubTest {

    private Map<Object, User> database;

    @Before
    public void setUp() {
        database = new HashMap<>();
    }

    @Test
    public void testFind() throws Exception {
        // given
        UserGatewayStub userGatewayStub = new UserGatewayStub(
            database
        );

        // when
        Optional<User> actual = userGatewayStub.find(UserGatewayStub.J_RAMBO.getUsername());

        // then
        assertThat(actual).isNotEqualTo(Optional.empty());
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get().getId()).isEqualTo(UserGatewayStub.J_RAMBO.getId());
        assertThat(actual.get().getEmail()).isEqualTo(UserGatewayStub.J_RAMBO.getEmail());
        assertThat(actual.get().getUsername()).isEqualTo(UserGatewayStub.J_RAMBO.getUsername());
    }

    @Test
    public void testSave() throws Exception {
        // given
        int USER_DATABASES = 1;
        int NUMBER_OF_USERS = 5;
        UserGatewayStub userGatewayStub = new UserGatewayStub(
            database
        );

        // when
        User user = UserGatewayStub.B_PITT.addDatabaseInstance(DatabaseIdGatewayStub.INSTANCE2);
        userGatewayStub.save(user);

        // then
        assertThat(user.getDatabasesSize()).isEqualTo(USER_DATABASES);
        assertThat(userGatewayStub.getAllUsers()).hasSize(NUMBER_OF_USERS);
    }

    @Test
    public void testAddUser() throws Exception {
        // given
        int NUMBER_OF_USERS = 6;
        User A_WALKER = new User("Alan Walker", "awalker", "alan.walker@example.com");
        UserGatewayStub userGatewayStub = new UserGatewayStub(
            database
        );

        // when
        userGatewayStub.addUser(A_WALKER);

        // then
        assertThat(userGatewayStub.getAllUsers()).hasSize(NUMBER_OF_USERS);
        assertThat(userGatewayStub.getAllUsers()).containsValues(A_WALKER);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        // given
        int NUMBER_OF_USERS = 5;
        UserGatewayStub userGatewayStub = new UserGatewayStub(
            database
        );

        // when
        Map<Object, User> actual = userGatewayStub.getAllUsers();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(NUMBER_OF_USERS);
    }

    @Test
    public void testGetUser() throws Exception {
        // given
        UserGatewayStub userGatewayStub = new UserGatewayStub(
            database
        );

        // when
        User actual = userGatewayStub.getUser(UserGatewayStub.B_PITT.getUsername());

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(UserGatewayStub.B_PITT.getId());
        assertThat(actual.getUsername()).isEqualTo(UserGatewayStub.B_PITT.getUsername());
        assertThat(actual.getEmail()).isEqualTo(UserGatewayStub.B_PITT.getEmail());
    }

    @Test
    public void testClearUsers() throws Exception {
        // given
        int NUMBER_OF_USERS = 0;
        UserGatewayStub userGatewayStub = new UserGatewayStub(
            database
        );

        // when
        userGatewayStub.clearUsers();
        Map<Object, User> actual = userGatewayStub.getAllUsers();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(NUMBER_OF_USERS);
    }

    @Test
    public void testRemoveUser() throws Exception {
        // given
        int NUMBER_OF_USERS = 4;
        UserGatewayStub userGatewayStub = new UserGatewayStub(
            database
        );

        // when
        userGatewayStub.removeUser(UserGatewayStub.B_PITT.getUsername());
        Map<Object, User> actual = userGatewayStub.getAllUsers();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(NUMBER_OF_USERS);
        assertThat(actual).doesNotContainValue(UserGatewayStub.B_PITT);
    }

}
