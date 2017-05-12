package pl.gov.coi.cascades.server.presentation.stub;

import org.junit.Before;
import org.junit.Test;
import pl.gov.coi.cascades.server.domain.User;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.04.17.
 */
public class UserSessionStubTest {

    private UserSessionStub userSessionStub;
    private String username = "jrambo";
    private String id = "fcweccf";
    private String email = "jrambo@example.org";

    @Before
    public void setUp() {
        username = "jrambo";
        id = "fcweccf";
        email = "jrambo@example.org";
        userSessionStub = new UserSessionStub();
    }

    @Test
    public void testGetSignedInUser() throws Exception {
        // when
        User actual = userSessionStub.getSignedInUser();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getUsername()).isEqualTo(username);
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getEmail()).isEqualTo(email);
    }

    @Test
    public void testSetUser() throws Exception {
        // given
        String username = "rmaklowicz";
        String id = "fgnrhi45tj";
        String email = "rmaklowicz@example.org";
        User user = new User(
            username,
            id,
            email
        );

        // when
        userSessionStub.setUser(user);
        User actual = userSessionStub.getSignedInUser();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getUsername()).isEqualTo(username);
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getId()).isEqualTo(id);
    }

}
