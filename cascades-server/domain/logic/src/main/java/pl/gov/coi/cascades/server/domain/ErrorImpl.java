package pl.gov.coi.cascades.server.domain;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.02.17.
 */
final class ErrorImpl implements Error {

    private final String message;

    ErrorImpl(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public String getPropertyPath() {
        return null;
    }
}
