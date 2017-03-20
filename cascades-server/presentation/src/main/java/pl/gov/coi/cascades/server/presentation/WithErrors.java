package pl.gov.coi.cascades.server.presentation;

import lombok.Getter;
import pl.gov.coi.cascades.server.domain.Error;

import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 13.03.17.
 */
public class WithErrors<T> {

    @Getter
    @Nullable
    private final T target;

    @Getter
    private final Iterable<Error> errors;

    public WithErrors(T target) {
        this.target = target;
        this.errors = new ArrayList<>();
    }

    public WithErrors(Iterable<Error> errors) {
        this.errors = errors;
        this.target = null;
    }

}
