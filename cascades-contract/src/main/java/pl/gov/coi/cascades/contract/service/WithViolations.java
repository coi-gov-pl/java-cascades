package pl.gov.coi.cascades.contract.service;

import lombok.Getter;

import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 13.03.17.
 *
 * @param <T> a wrapped type
 */
public class WithViolations<T> {

    @Getter
    @Nullable
    private final T target;

    @Getter
    private final Iterable<Violation> violations;

    /**
     * Constructor that takes a target entity to wrap
     *
     * @param target a target entity
     */
    public WithViolations(T target) {
        this.target = target;
        this.violations = new ArrayList<>();
    }

    /**
     * Constructor
     * @param violations a iterable of violations
     */
    public WithViolations(Iterable<Violation> violations) {
        this.violations = violations;
        this.target = null;
    }

}
