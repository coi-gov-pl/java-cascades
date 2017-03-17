package pl.gov.coi.cascades.server;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 16.03.17
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Enviroment {
    DEVELOPMENT(Enviroment.DEVELOPMENT_NAME),
    PRODUCTION(Enviroment.PRODUCTION_NAME);

    public static final String DEVELOPMENT_NAME = "development";
    public static final String PRODUCTION_NAME = "production";
    private final String name;
}
