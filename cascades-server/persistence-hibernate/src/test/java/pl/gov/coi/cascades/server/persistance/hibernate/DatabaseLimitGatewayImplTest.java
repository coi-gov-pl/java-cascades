package pl.gov.coi.cascades.server.persistance.hibernate;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.server.domain.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
public class DatabaseLimitGatewayImplTest {

    private static final String USERNAME = "username";
    private static final String ID = "id";
    private static final String EMAIL = "email";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private DatabaseLimitGatewayImpl databaseLimitGateway;

    @Before
    public void init() {
        databaseLimitGateway =
            new DatabaseLimitGatewayImpl();
    }


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
        assertEquals(100, result);
    }

    @Test
    public void shouldGlobalLimitExceeded() {
        //when
        boolean result = databaseLimitGateway.isGlobalLimitExceeded();

        //then
        assertFalse(result);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldGetGlobalLimit() {
        //when
        databaseLimitGateway.getGlobalLimit();
    }

    private User getUser() {
        return new User(USERNAME, ID, EMAIL);
    }
}
