package pl.gov.coi.cascades.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.UserGateway;

import javax.inject.Inject;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 03.04.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({Environment.DEVELOPMENT_NAME, Environment.HIBERNATE_NAME})
public class UserGatewayImplFunctionalTest {

    private UserGateway userGateway;

    @Inject
    public void setUserGateway(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Test
    public void testUserGatewayPositivePath() throws Exception {
        // when
        Optional<User> actual = userGateway.find("jrambo");

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get().getUsername()).isEqualTo("jrambo");
        assertThat(actual.get().getEmail()).isEqualTo("jrambo@example.org");
        assertThat(actual.get().getDatabases()).hasSize(1);
    }

    @Test
    public void testUserGatewayNegativePath() throws Exception {
        // when
        Optional<User> actual = userGateway.find("nonExistingUser");

        // then
        assertThat(actual).isEmpty();
    }

}
