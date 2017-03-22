package pl.gov.coi.cascades.server.domain;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.02.17.
 */
public final class ErrorImpl implements Error {

    private final String message;
    private final String path;

    public ErrorImpl(String message,
                     String path) {
        this.message = message;
        this.path = path;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getPropertyPath() {
        return path;
    }
}
