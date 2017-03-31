package pl.gov.coi.cascades.server.presentation.deletedatabase;

import org.springframework.http.HttpStatus;
import pl.gov.coi.cascades.contract.service.Violation;
import pl.gov.coi.cascades.server.domain.deletedatabase.Response;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 22.03.17.
 */
class Presenter implements Response {

    private final Collection<Violation> violations = new HashSet<>();

    @Override
    public boolean isSuccessful() {
        return violations.isEmpty();
    }

    @Override
    public void addViolation(Violation violation) {
        violations.add(violation);
    }

    public Iterable<Violation> getViolations() {
        return violations;
    }

    ViewModel createModel() {
        return isSuccessful()
            ? new ViewModel(this, HttpStatus.OK)
            : new ViewModel(this, HttpStatus.BAD_REQUEST);
    }

}
