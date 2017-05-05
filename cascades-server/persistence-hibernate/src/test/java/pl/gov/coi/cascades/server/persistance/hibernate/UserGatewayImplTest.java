package pl.gov.coi.cascades.server.persistance.hibernate;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.server.domain.DatabaseTypeClassNameService;
import pl.gov.coi.cascades.server.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
    private TypedQuery<Object> query;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

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
        UserGatewayImpl userGateway = new UserGatewayImpl(
            databaseTypeClassNameService
        );
        userGateway.setEntityManager(entityManager);

        // when
        userGateway.save(user);

        // then
        verify(entityManager, times(1)).getReference(any(), anyLong());
        verify(entityManager, times(1)).persist(any());
    }

}
