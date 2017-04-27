package pl.gov.coi.cascades.server.persistance.stub;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.04.17.
 */
public class UsernameAndPasswordCredentialsStubTest {

    @Test
    public void testGetUsername() throws Exception {
        // given
        String name = "Johny Bravo";
        UsernameAndPasswordCredentialsStub usernameAndPasswordCredentialsStub = new UsernameAndPasswordCredentialsStub(
            name
        );

        // when
        String actual = usernameAndPasswordCredentialsStub.getUsername();

        // then
        assertThat(actual).isEqualTo(name);
    }

    @Test
    public void testGetPassword() throws Exception {
        // given
        String name = "Johny Bravo";
        UsernameAndPasswordCredentialsStub usernameAndPasswordCredentialsStub = new UsernameAndPasswordCredentialsStub(
            name
        );

        // when
        char[] actual = usernameAndPasswordCredentialsStub.getPassword();

        // then
        assertThat(actual).isNotNull();
    }

}
