package pl.gov.coi.cascades.server.domain.deletedatabase;

import pl.gov.coi.cascades.server.domain.Error;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 09.03.17.
 */
public class Response {

    private final Collection<Error> errors = new HashSet<>();

    boolean isSuccessful() {
        return errors.isEmpty();
    }

    void addError(Error error) {
        errors.add(error);
    }

}
