package pl.gov.coi.cascades.server;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 16.03.17
 */
public enum Environment {
    DEVELOPMENT(Environment.DEVELOPMENT_NAME),
    PRODUCTION(Environment.PRODUCTION_NAME);

    public static final String DEVELOPMENT_NAME = "development";
    public static final String PRODUCTION_NAME = "production";
    private final String name;

    @java.beans.ConstructorProperties({"name"})
    private Environment(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
