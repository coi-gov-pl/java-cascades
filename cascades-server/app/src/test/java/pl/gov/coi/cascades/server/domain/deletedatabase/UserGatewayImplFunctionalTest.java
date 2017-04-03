package pl.gov.coi.cascades.server.domain.deletedatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.gov.coi.cascades.server.Environment;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.persistance.hibernate.UserGatewayImpl;

import javax.inject.Inject;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 03.04.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(Environment.DEVELOPMENT_NAME)
public class UserGatewayImplFunctionalTest {

    private MockMvc mockMvc;

    @Inject
    private WebApplicationContext wac;

    private UserGatewayImpl userGateway;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Inject
    public void setUserGateway(UserGatewayImpl userGateway) {
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
