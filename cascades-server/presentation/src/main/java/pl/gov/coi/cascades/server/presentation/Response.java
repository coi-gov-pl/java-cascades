package pl.gov.coi.cascades.server.presentation;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 13.03.17.
 */
public interface Response<T> {

    T getObject();

    void addError();

    boolean isSuccessful();

}
