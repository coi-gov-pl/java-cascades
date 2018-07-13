package pl.gov.coi.cascades.server.domain.deletedatabase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.server.domain.DatabaseIdGateway;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseInstanceGateway;
import pl.gov.coi.cascades.server.domain.DatabaseOperationsGateway;
import pl.gov.coi.cascades.server.domain.DatabaseUserGateway;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.UserGateway;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
public class DeleteDatabaseGatewayFacadeTest {

    private static final String EXAMPLE_ID = "databaseExampleId";
    private static final String EXAMPLE_USERNAME = "exampleUsername";
    private DeleteDatabaseGatewayFacade facade;

    @Mock
    private User user;

    @Mock
    private UserGateway userGateway;

    @Mock
    private DatabaseIdGateway databaseIdGateway;

    @Mock
    private DatabaseInstance databaseInstance;

    @Mock
    private DatabaseInstanceGateway databaseInstanceGateway;

    @Mock
    private DatabaseOperationsGateway databaseOperationsGateway;

    @Mock
    private DatabaseUserGateway databaseUserGateway;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void init() {
        facade = new DeleteDatabaseGatewayFacade(
            userGateway,
            databaseIdGateway,
            databaseInstanceGateway,
            databaseOperationsGateway,
            databaseUserGateway
        );
    }

    @Test
    public void shouldFindInstance() {
        // given
        when(databaseIdGateway.findInstance(any(DatabaseId.class))).thenReturn(Optional.of(databaseInstance));

        // when
        Optional<DatabaseInstance> result = facade.findInstance(new DatabaseId(EXAMPLE_ID));

        // then
        assertNotNull(result);
        verify(databaseIdGateway, times(1)).findInstance(any(DatabaseId.class));
    }

    @Test
    public void shouldFindUser() {
        // given
        when(userGateway.find(anyString())).thenReturn(Optional.of(user));

        // when
        Optional<User> result = facade.findUser(EXAMPLE_USERNAME);

        // then
        assertNotNull(result);
        verify(userGateway, times(1)).find(anyString());
    }

    @Test
    public void shouldDeleteDatabase() throws Exception {
        // when
        facade.deleteDatabase(databaseInstance);

        // then
        verify(databaseUserGateway, times(1)).deleteUser(any(DatabaseInstance.class));
        verify(databaseOperationsGateway, times(1)).deleteDatabase(any(DatabaseInstance.class));
        verify(databaseInstanceGateway, times(1)).deleteDatabase(any(DatabaseInstance.class));
    }
}
