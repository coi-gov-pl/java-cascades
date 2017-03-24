package pl.gov.coi.cascades.server.domain;

import lombok.Getter;

import java.util.Collection;
import java.util.HashSet;

import static pl.wavesoftware.eid.utils.EidPreconditions.checkNotNull;

public class User {

    @Getter
    private String username;
    @Getter
    private String id;
    @Getter
    private String email;
	private Collection<DatabaseInstance> databases = new HashSet<>();

    public User(String username, String id, String email) {
        this.username = username;
        this.id = id;
        this.email = email;
    }

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
        for (DatabaseInstance instance: user.getDatabases()) {
            checkNotNull(instance, "20170320:160547");
            checkNotNull(instance.getDatabaseId(), "20170320:160607");
            if (instance.getDatabaseId().equals(databaseInstance.getDatabaseId())) {
                user.databases.remove(instance);
                return user;
            }
        }
        user.databases.add(databaseInstance);

        return user;
    }

    public Iterable<DatabaseInstance> getDatabases() {
        return databases;
    }

    public int getDatabasesSize() {
        return databases.size();
    }

}
