package pl.gov.coi.cascades.server.persistance.stub;

import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.UserGateway;

import java.util.Map;
import java.util.Optional;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.02.17.
 */
public final class UserGatewayStub implements UserGateway {

    public static final User B_PITT = new User("Brad Pitt", "bpitt", "brad.pit@example.com");
    public static final User H_GRANT = new User("Hugh Grant", "hgrant", "hugh.grant@example.com");
    public static final User M_ROZNESKI = new User("Mikołaj Roznerski", "mrozneski", "mikolaj.rozneski@example.com");
    public static final User M_ZAKOSCIELNY = new User("Maciej Zakościelny", "mzakoscielny", "maciej.zakocielny@example.com");
    public static final User J_RAMBO = new User("jrambo", "fcweccf", "jrambo@example.org");
    private Map<Object, User> users;

    /**
     * Default constructor.
     *
     * @param database Given map of databases.
     */
    public UserGatewayStub(Map<Object, User> database) {
        this.users = database;
        User user = J_RAMBO.addDatabaseInstance(DatabaseIdGatewayStub.INSTANCE1);

        addUser(B_PITT);
        addUser(H_GRANT);
        addUser(M_ROZNESKI);
        addUser(M_ZAKOSCIELNY);
        addUser(user);
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

    public Map<Object, User> getAllUsers() {
        return users;
    }

    public User getUser(String key) {
        return users.get(key);
    }

    public void clearUsers() {
        users.clear();
    }

    public void removeUser(String key) {
        users.remove(key);
    }

}
