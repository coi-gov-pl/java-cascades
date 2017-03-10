package pl.gov.coi.cascades.server.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashSet;

@RequiredArgsConstructor
public class User {

    @Getter
    private final String username;
    @Getter
    private final String id;
    @Getter
    private final String email;
	private final Collection<DatabaseInstance> databases = new HashSet<>();

    /**
     * Copy constructor.
     */
    private User(User user) {
        this(user.getUsername(),
            user.getId(),
            user.getEmail()
        );
    }

    public User addDatabaseInstance(DatabaseInstance databaseInstance) {
        User user = new User(this);
        user.databases.addAll(databases);
        user.databases.add(databaseInstance);
        return user;
    }

    public User updateDatabaseInstance(DatabaseInstance databaseInstance) {
        User user = new User(this);
        user.databases.addAll(databases);
        user.databases.stream()
            .filter(instance -> instance.getDatabaseId().equals(databaseInstance.getDatabaseId()))
            .forEach(instance -> {
                user.databases.remove(instance);
                user.databases.add(databaseInstance);
            }
        );
        return user;
    }

    public Iterable<DatabaseInstance> getDatabases() {
        return databases;
    }

    public int getDatabasesSize() {
        return databases.size();
    }

}
