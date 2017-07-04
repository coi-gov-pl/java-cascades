package pl.gov.coi.cascades.server.domain;

import java.util.function.Consumer;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 28.06.17
 * @param <T> a type of entity
 */
public interface ValidatedEntityListenable<T> {
    /**
     * Adds a validated entity listener
     * @param listener a validated entity listener
     */
    void addValidatedEntityListener(ValidatedEntityListener<T> listener);

    /**
     * Removes a validated entity listener
     * @param listener a validated entity listener
     */
    void removeValidatedEntityListener(ValidatedEntityListener<T> listener);

    /**
     * Informs that entity is valid
     * @param entity a valid entity
     */
    void entityIsValid(T entity);

    /**
     * A validated entity listener
     * @param <T> a type of entity
     */
    interface ValidatedEntityListener<T> extends Consumer<T> {

    }
}
