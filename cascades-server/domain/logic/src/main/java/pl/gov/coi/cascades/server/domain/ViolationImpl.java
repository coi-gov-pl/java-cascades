package pl.gov.coi.cascades.server.domain;

import pl.gov.coi.cascades.contract.service.Violation;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.02.17.
 */
public final class ViolationImpl implements Violation {

    private final String message;
    private final String path;

    public ViolationImpl(String message,
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
