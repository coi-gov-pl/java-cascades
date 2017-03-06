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

    private Map<String, User> users;

    public UserGatewayStub() {
        this.users = new HashMap<>();

        User bpitt = new User("Brad Pitt", "1abc", "brad.pit@example.com");
        User hgrant = new User("Hugh Grant", "2abc", "hugh.grant@example.com");
        User mrozneski = new User("Mikołaj Roznerski", "3abc", "mikolaj.rozneski@example.com");
        User mzakoscielny = new User("Maciej Zakościelny", "4abc", "maciej.zakocielny@example.com");

        users.put("12345678", bpitt);
        users.put("23456789", hgrant);
        users.put("34567891", mrozneski);
        users.put("45678912", mzakoscielny);
    }

    @Override
    public Optional<User> find(String user) {
        return Optional.ofNullable(users.get(user));
    }

    @Override
    public void save(User user) {
        users.replace(user.getUsername(), user);
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public Map<String, User> getAllUsers() {
        return users;
    }

}
