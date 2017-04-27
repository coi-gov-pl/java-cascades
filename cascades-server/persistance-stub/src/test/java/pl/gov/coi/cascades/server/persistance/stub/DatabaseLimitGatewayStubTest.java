package pl.gov.coi.cascades.server.persistance.stub;

import org.junit.Test;
import pl.gov.coi.cascades.server.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.gov.coi.cascades.server.persistance.stub.UserGatewayStub.J_RAMBO;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.04.17.
 */
public class DatabaseLimitGatewayStubTest {

    @Test
    public void testSetGlobalLimit() throws Exception {
        // given
        int GLOBAL_LIMIT = 70;
        DatabaseLimitGatewayStub databaseLimitGatewayStub = new DatabaseLimitGatewayStub();

        // when
        databaseLimitGatewayStub.setGlobalLimit(GLOBAL_LIMIT);
        int actual = databaseLimitGatewayStub.getGlobalLimit();

        // then
        assertThat(actual).isEqualTo(GLOBAL_LIMIT);
    }

    @Test
    public void testSetUserLimit() throws Exception {
        // given
        int USER_LIMIT = 10;
        DatabaseLimitGatewayStub databaseLimitGatewayStub = new DatabaseLimitGatewayStub();

        // when
        databaseLimitGatewayStub.setUserLimit(USER_LIMIT);
        int actual = databaseLimitGatewayStub.getPersonalLimitPerUser(J_RAMBO);

        // then
        assertThat(actual).isEqualTo(USER_LIMIT);
    }

    @Test
    public void testWhenLimitIsNotExceeded() throws Exception {
        // given
        int USER_LIMIT = 2;
        DatabaseLimitGatewayStub databaseLimitGatewayStub = new DatabaseLimitGatewayStub();
        databaseLimitGatewayStub.setUserLimit(USER_LIMIT);

        // when
        boolean actual = databaseLimitGatewayStub.isPersonalLimitExceeded(J_RAMBO);

        // then
        assertThat(actual).isFalse();
    }

    @Test
    public void testWhenLimitIsExceeded() throws Exception {
        // given
        int USER_LIMIT = 2;
        DatabaseLimitGatewayStub databaseLimitGatewayStub = new DatabaseLimitGatewayStub();
        databaseLimitGatewayStub.setUserLimit(USER_LIMIT);
        User user = UserGatewayStub.J_RAMBO.addDatabaseInstance(DatabaseIdGatewayStub.INSTANCE1);
        user = user.addDatabaseInstance(DatabaseIdGatewayStub.INSTANCE2);

        // when
        boolean actual = databaseLimitGatewayStub.isPersonalLimitExceeded(user);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    public void testGetPersonalLimitPerUser() throws Exception {
        // given
        int DEFAULT_USER_LIMIT = 2;
        DatabaseLimitGatewayStub databaseLimitGatewayStub = new DatabaseLimitGatewayStub();
        databaseLimitGatewayStub.setUserLimit(DEFAULT_USER_LIMIT);

        // when
        int actual = databaseLimitGatewayStub.getPersonalLimitPerUser(UserGatewayStub.J_RAMBO);

        // then
        assertThat(actual).isEqualTo(DEFAULT_USER_LIMIT);
    }

    @Test
    public void testIsGlobalLimitExceeded() throws Exception {
        // given
        int DEFAULT_GLOBAL_LIMIT = 5;
        DatabaseLimitGatewayStub databaseLimitGatewayStub = new DatabaseLimitGatewayStub();
        databaseLimitGatewayStub.setGlobalLimit(DEFAULT_GLOBAL_LIMIT);

        // when
        boolean actual = databaseLimitGatewayStub.isGlobalLimitExceeded();

        // then
        assertThat(actual).isFalse();
    }

    @Test
    public void testGetGlobalLimit() throws Exception {
        // given
        int DEFAULT_GLOBAL_LIMIT = 5;
        DatabaseLimitGatewayStub databaseLimitGatewayStub = new DatabaseLimitGatewayStub();
        databaseLimitGatewayStub.setGlobalLimit(DEFAULT_GLOBAL_LIMIT);

        // when
        int actual = databaseLimitGatewayStub.getGlobalLimit();

        // then
        assertThat(actual).isEqualTo(DEFAULT_GLOBAL_LIMIT);
    }

}
