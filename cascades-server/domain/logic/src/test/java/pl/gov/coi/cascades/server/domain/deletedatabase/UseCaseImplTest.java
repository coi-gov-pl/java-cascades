package pl.gov.coi.cascades.server.domain.deletedatabase;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.service.Violation;
import pl.gov.coi.cascades.server.domain.DatabaseIdGateway;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseInstanceGateway;
import pl.gov.coi.cascades.server.domain.DatabaseOperationsGateway;
import pl.gov.coi.cascades.server.domain.DatabaseUserGateway;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.UserGateway;
import pl.gov.coi.cascades.server.persistance.stub.DatabaseIdGatewayStub;
import pl.gov.coi.cascades.server.persistance.stub.UserGatewayStub;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 05.05.17.
 */
public class UseCaseImplTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private User user;

    @Mock
    private DatabaseId databaseId;

    @Mock
    private DatabaseInstance databaseInstance;

    @Mock
    private DatabaseIdGateway databaseIdGateway;

    @Mock
    private DatabaseOperationsGateway databaseOperationsGateway;

    @Mock
    private DatabaseUserGateway databaseUserGateway;

    @Mock
    private Request request;

    @Mock
    private DatabaseInstanceGateway databaseInstanceGateway;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testExecuteWhenUserIsNotPresent() throws Exception {
        // given
        String message = "Given user is invalid.";
        Response response = new ResponseImpl();
        UseCaseImpl useCase = new UseCaseImpl(
            userGateway,
            databaseIdGateway,
            databaseInstanceGateway,
            databaseOperationsGateway,
            databaseUserGateway
        );
        when(request.getDatabaseId()).thenReturn(databaseId);
        when(databaseIdGateway.findInstance(any(DatabaseId.class))).thenReturn(Optional.of(databaseInstance));
        when(userGateway.find(anyString())).thenReturn(Optional.of(user));
        when(request.getUser()).thenReturn(null);

        // when
        useCase.execute(
            request,
            response
        );

        // then
        verify(userGateway, times(0)).find(anyString());
        verify(databaseIdGateway, times(1)).findInstance(any(DatabaseId.class));
        verify(databaseInstanceGateway, times(0)).deleteDatabase(any(DatabaseInstance.class));
        verify(userGateway, times(0)).save(any(User.class));
        assertThat(response.getViolations()).isNotEmpty();
        assertThat(response.getViolations()).hasSize(2);
    }

    @Test
    public void testExecuteWhenErrorsOccurred() throws Exception {
        // given
        Response response = new ResponseImpl();
        UseCaseImpl useCase = new UseCaseImpl(
            userGateway,
            databaseIdGateway,
            databaseInstanceGateway,
            databaseOperationsGateway,
            databaseUserGateway
        );
        when(request.getDatabaseId()).thenReturn(databaseId);
        when(databaseIdGateway.findInstance(any(DatabaseId.class))).thenReturn(Optional.of(databaseInstance));
        when(userGateway.find(anyString())).thenReturn(Optional.of(user));
        when(request.getUser()).thenReturn(user);

        // when
        useCase.execute(
            request,
            response
        );

        // then
        verify(userGateway, times(1)).find(anyString());
        verify(databaseIdGateway, times(1)).findInstance(any(DatabaseId.class));
        verify(databaseUserGateway, times(0)).deleteUser(any(DatabaseInstance.class));
        verify(databaseOperationsGateway, times(0)).deleteDatabase(any(DatabaseInstance.class));
        verify(databaseInstanceGateway, times(0)).deleteDatabase(any(DatabaseInstance.class));
        verify(userGateway, times(0)).save(any(User.class));
        assertThat(response.getViolations()).isNotEmpty();
    }

    @Test
    public void testExecuteWithoutErrors() throws Exception {
        // given
        Response response = new ResponseImpl();
        UseCaseImpl useCase = new UseCaseImpl(
            userGateway,
            databaseIdGateway,
            databaseInstanceGateway,
            databaseOperationsGateway,
            databaseUserGateway
        );
        User jrambo = UserGatewayStub.J_RAMBO.addDatabaseInstance(DatabaseIdGatewayStub.INSTANCE1);
        when(request.getDatabaseId()).thenReturn(databaseId);
        when(databaseIdGateway.findInstance(any(DatabaseId.class))).thenReturn(Optional.of(DatabaseIdGatewayStub.INSTANCE1));
        when(userGateway.find(anyString())).thenReturn(Optional.of(jrambo));
        when(request.getUser()).thenReturn(jrambo);

        // when
        useCase.execute(
            request,
            response
        );

        // then
        verify(userGateway, times(1)).find(anyString());
        verify(databaseIdGateway, times(1)).findInstance(any(DatabaseId.class));
        verify(databaseUserGateway, times(1)).deleteUser(any(DatabaseInstance.class));
        verify(databaseOperationsGateway, times(1)).deleteDatabase(any(DatabaseInstance.class));
        verify(databaseInstanceGateway, times(1)).deleteDatabase(any(DatabaseInstance.class));
        verify(userGateway, times(1)).save(any(User.class));
        assertThat(response.getViolations()).isEmpty();
    }

    @Test
    public void testBuilder() throws Exception {
        // when
        UseCaseImpl useCase = UseCaseImpl.builder()
            .userGateway(userGateway)
            .databaseIdGateway(databaseIdGateway)
            .databaseInstanceGateway(databaseInstanceGateway)
            .build();

        // then
        assertThat(useCase).isNotNull();
    }

    private static final class ResponseImpl implements Response {

        private final Collection<Violation> violations = new HashSet<>();

        @Override
        public boolean isSuccessful() {
            return violations.isEmpty();
        }

        @Override
        public void addViolation(Violation violation) {
            violations.add(violation);
        }

        @Override
        public Iterable<Violation> getViolations() {
            return violations;
        }

    }
}
