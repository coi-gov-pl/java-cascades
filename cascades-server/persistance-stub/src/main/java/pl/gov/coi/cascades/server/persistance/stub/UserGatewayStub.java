package pl.gov.coi.cascades.server.persistance.stub;

import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.UserGateway;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.02.17.
 */
public final class UserGatewayStub implements UserGateway {

    private Map<String, User> users = new HashMap<>();

    @Override
    public Optional<User> find(String user) {
        return Optional.ofNullable(users.get(user));
    }

    @Override
    public void save(User user) {

    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }
}
