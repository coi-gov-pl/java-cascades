package pl.gov.coi.cascades.server.presentation.deletedatabase;

import org.springframework.http.HttpStatus;
import pl.gov.coi.cascades.contract.service.Violation;
import pl.gov.coi.cascades.server.domain.deletedatabase.Response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

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
        return cloneList(violations);
    }

    private static List<Violation> cloneList(Collection<Violation> violationList) {
        List<Violation> clonedList = new ArrayList<>(violationList.size());
        for (Violation violation : violationList) {
            clonedList.add(new ViolationImpl(violation));
        }
        return clonedList;
    }

    ViewModel createModel() {
        return isSuccessful()
            ? new ViewModel(this, HttpStatus.OK)
            : new ViewModel(this, HttpStatus.BAD_REQUEST);
    }

    private static final class ViolationImpl implements Violation {

        private String message;
        private String propertyPath;

        private ViolationImpl(Violation violation) {
            this(
                violation.getMessage(),
                violation.getPropertyPath()
            );
        }

        private ViolationImpl(String message,
                             String propertyPath) {
            this.message = message;
            this.propertyPath = propertyPath;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public String getPropertyPath() {
            return propertyPath;
        }
    }

}
