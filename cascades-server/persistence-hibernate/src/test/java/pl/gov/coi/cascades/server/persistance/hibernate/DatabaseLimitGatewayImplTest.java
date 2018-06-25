package pl.gov.coi.cascades.server.persistance.hibernate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import pl.gov.coi.cascades.server.domain.User;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

/**
 * @author Łukasz Małek <lukasz.malek@coi.gov.pl>
 */
@RunWith(MockitoJUnitRunner.class)
public class DatabaseLimitGatewayImplTest {

    private static final String USERNAME = "username";
    private static final String ID = "id";
    private static final String EMAIL = "email";

    @InjectMocks
    private DatabaseLimitGatewayImpl databaseLimitGateway;

    @Test
    public void shouldBePersonalLimitExceeded() {
        //when
        boolean result = databaseLimitGateway.isPersonalLimitExceeded(getUser());

        //then
        assertFalse(result);
    }

    @Test
    public void shouldGetPersonalLimitPerUser() {
        //when
        int result = databaseLimitGateway.getPersonalLimitPerUser(getUser());

        //then
        assertEquals(0, result);
    }

    @Test
    public void shouldGlobalLimitExceeded() {
        //when
        boolean result = databaseLimitGateway.isGlobalLimitExceeded();

        //then
        assertFalse(result);
    }

    @Test
    public void shouldGetGlobalLimit() {
        //when
        int result = databaseLimitGateway.getPersonalLimitPerUser(getUser());

        //then
        assertEquals(0, result);
    }

    private User getUser() {
        return new User(USERNAME, ID, EMAIL);
    }
}
