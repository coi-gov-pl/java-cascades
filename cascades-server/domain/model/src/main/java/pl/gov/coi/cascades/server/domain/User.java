package pl.gov.coi.cascades.server.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class User {

    @Getter
    private final String username;
    @Getter
    private final String id;
    @Getter
    private final String email;
	private final Collection<DatabaseInstance> databases;

}
