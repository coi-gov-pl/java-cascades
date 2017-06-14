package pl.gov.coi.cascades.server.presentation.launchdatabase;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.contract.service.RemoteDatabaseSpec;
import pl.gov.coi.cascades.contract.service.WithViolations;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.launchdatabase.UseCase;
import pl.gov.coi.cascades.server.presentation.UserSession;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 04.05.17.
 */
public class LaunchDatabaseControllerTest {

    @Mock
    private UseCase useCase;

    @Mock
    private UserSession userSession;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testLaunchDatabasePost() throws Exception {
        // given
        String type = "oracle";
        String templateId = "oracle_template";
        String instanceName = "ora84u8439";
        String username = "jrambo";
        String id = "fcweccf";
        String email = "jrambo@example.org";
        User user = new User(
            username,
            id,
            email
        );
        DefaultOptionalMapper optionalMapper = new DefaultOptionalMapper();
        when(userSession.getSignedInUser()).thenReturn(user);
        RemoteDatabaseRequestDTO request = new RemoteDatabaseRequestDTO(
            type,
            templateId,
            instanceName
        );
        LaunchDatabaseController launchDatabaseController = new LaunchDatabaseController(
            userSession,
            useCase,
            optionalMapper
        );

        // when
        ResponseEntity<WithViolations<RemoteDatabaseSpec>> actual = launchDatabaseController.launchDatabasePost(
            request
        );

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getHeaders()).hasSize(0);
        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody().getViolations()).hasSize(0);
    }

}
