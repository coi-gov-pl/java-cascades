package pl.gov.coi.cascades.server;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 12.04.17.
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ProfileType {

    STUB(ProfileType.STUB_NAME),
    HIBERNATE(ProfileType.HIBERNATE_NAME);

    public static final String HIBERNATE_NAME = "hibernate";
    public static final String STUB_NAME = "stub";
    private final String name;

}
