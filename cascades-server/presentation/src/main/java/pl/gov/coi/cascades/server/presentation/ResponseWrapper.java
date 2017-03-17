package pl.gov.coi.cascades.server.presentation;

import lombok.Getter;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 13.03.17.
 */
public class ResponseWrapper<T> {

    @Getter
    private T target;

    public ResponseWrapper(T target) {
        this.target = target;
    }

}
