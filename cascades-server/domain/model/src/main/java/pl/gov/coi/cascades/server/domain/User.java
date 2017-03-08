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

    public void addDatabaseInstance(DatabaseInstance databaseInstance) {
        databases.add(databaseInstance);
    }

    public Iterable<DatabaseInstance> getDatabases() {
        return databases;
    }

    public int getDatabasesSize() {
        return databases.size();
    }
}
