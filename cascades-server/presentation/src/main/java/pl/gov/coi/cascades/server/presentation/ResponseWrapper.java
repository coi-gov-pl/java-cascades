package pl.gov.coi.cascades.server.presentation;

import lombok.Getter;
import pl.gov.coi.cascades.server.domain.Error;

import java.io.Serializable;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 13.03.17.
 */
public class ResponseWrapper<T> implements Serializable {

    private static final long serialVersionUID = 42L;

    @Getter
    private T target;
    @Getter
    private Iterable<Error> errors;

    public ResponseWrapper(Iterable<Error> errors,
                           T target) {
        this.target = target;
        this.errors = errors;
    }

}
