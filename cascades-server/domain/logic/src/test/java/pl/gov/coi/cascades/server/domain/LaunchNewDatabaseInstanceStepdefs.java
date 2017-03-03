package pl.gov.coi.cascades.server.domain;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import pl.gov.coi.cascades.server.domain.LaunchNewDatabaseInstanceRequest.LaunchNewDatabaseInstanceRequestBuilder;
import pl.gov.coi.cascades.server.persistance.stub.UserGatewayStub;

import java.util.Optional;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 28.02.17.
 */
public class LaunchNewDatabaseInstanceStepdefs {
    private LaunchNewDatabaseInstanceRequestBuilder builder;
    private UserGatewayStub userGatewayStub = new UserGatewayStub();

    @Before
    public void before() {
        userGatewayStub.addUser(
            new User("jackie", "eedeed", "jackie@example.org")
        );
    }

    @Given("^authenticated user is \"([^\"]*)\"$")
    public void authenticated_user_is(String userName) {
        Optional<User> user = userGatewayStub.find(userName);
        getRequestBuilder()
            .user(user.orElse(null));

    }

    private LaunchNewDatabaseInstanceRequestBuilder getRequestBuilder() {
        if (builder == null) {
            builder = LaunchNewDatabaseInstanceRequest.builder();
        }
        return builder;
    }
}
