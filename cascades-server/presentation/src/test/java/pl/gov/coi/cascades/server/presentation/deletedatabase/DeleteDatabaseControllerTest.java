package pl.gov.coi.cascades.server.presentation.deletedatabase;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.deletedatabase.Response;
import pl.gov.coi.cascades.server.domain.deletedatabase.UseCase;
import pl.gov.coi.cascades.server.presentation.UserSession;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 04.05.17.
 */
public class DeleteDatabaseControllerTest {

    @Mock
    private UseCase useCase;

    @Mock
    private UserSession userSession;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testDeleteDatabase() throws Exception {
        // given
        String databaseIdAsString = "oracle";
        String username = "jrambo";
        String id = "fcweccf";
        String email = "jrambo@example.org";
        User user = new User(
            username,
            id,
            email
        );
        when(userSession.getSignedInUser()).thenReturn(user);
        DeleteDatabaseController deleteDatabaseController = new DeleteDatabaseController(
            userSession,
            useCase
        );

        // when
        ResponseEntity<Response> actual = deleteDatabaseController.deleteDatabase(
            databaseIdAsString
        );

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getHeaders()).hasSize(0);
        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody().getViolations()).hasSize(0);
    }

}
