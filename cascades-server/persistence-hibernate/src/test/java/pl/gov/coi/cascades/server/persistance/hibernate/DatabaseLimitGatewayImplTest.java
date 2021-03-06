package pl.gov.coi.cascades.server.persistance.hibernate;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.persistance.hibernate.mapper.DatabaseInstanceMapper;

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

    @Test(expected = UnsupportedOperationException.class)
    public void shouldBePersonalLimitExceeded() {
        //when
        databaseLimitGateway.isPersonalLimitExceeded(getUser());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldGetPersonalLimitPerUser() {
        //when
        databaseLimitGateway.getPersonalLimitPerUser(getUser());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldGlobalLimitExceeded() {
        //when
        databaseLimitGateway.isGlobalLimitExceeded();
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
