package pl.gov.coi.cascades.server.persistance.hibernate;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.server.domain.User;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
public class DatabaseLimitGatewayImplTest {

    private static final String USERNAME = "username";
    private static final String ID = "id";
    private static final String EMAIL = "email";

    private DatabaseLimitGatewayImpl databaseLimitGateway;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void init() {
        databaseLimitGateway = new DatabaseLimitGatewayImpl();
    }


    @Test
    public void shouldBePersonalLimitExceeded() {
        //when
        boolean result = databaseLimitGateway.isPersonalLimitExceeded(getUser());

        //then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldGetPersonalLimitPerUser() {
        //when
        int result = databaseLimitGateway.getPersonalLimitPerUser(getUser());

        //then
        assertThat(result).isEqualTo(100);
    }

    @Test
    public void shouldGlobalLimitExceeded() {
        //when
        boolean result = databaseLimitGateway.isGlobalLimitExceeded();

        //then
        assertThat(result).isFalse();
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
