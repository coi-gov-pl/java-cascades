package pl.gov.coi.cascades.server.presentation.deletedatabase;

import org.springframework.http.HttpStatus;
import pl.gov.coi.cascades.server.domain.Error;
import pl.gov.coi.cascades.server.domain.deletedatabase.Response;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 22.03.17.
 */
class Presenter implements Response {

    private final Collection<Error> errors = new HashSet<>();

    @Override
    public boolean isSuccessful() {
        return errors.isEmpty();
    }

    @Override
    public void addError(Error error) {
        errors.add(error);
    }

    @Override
    public Iterable<Error> getErrors() {
        return errors;
    }

    ViewModel createModel() {
        return isSuccessful()
            ? new ViewModel(this, HttpStatus.OK)
            : new ViewModel(this, HttpStatus.BAD_REQUEST);
    }

}
