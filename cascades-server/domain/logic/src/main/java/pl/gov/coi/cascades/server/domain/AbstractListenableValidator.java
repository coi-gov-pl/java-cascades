package pl.gov.coi.cascades.server.domain;

import pl.gov.coi.cascades.contract.service.Violation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author agnieszka
 * @since 27.06.17
 */
public abstract class AbstractListenableValidator<T> implements
    Validator, ViolationListenable, ValidatedEntityListenable<T> {

    private final List<ViolationListener> violationListeners = new ArrayList<>();
    private final List<ValidatedEntityListener<T>> entityListeners = new ArrayList<>();

    @Override
    public void addViolationListener(ViolationListener listener) {
        violationListeners.add(listener);
    }

    @Override
    public void removeViolationConsumer(ViolationListener listener) {
        violationListeners.remove(listener);
    }

    @Override
    public void addValidatedEntityListener(ValidatedEntityListener<T> listener) {
        entityListeners.add(listener);
    }

    @Override
    public void removeValidatedEntityListener(ValidatedEntityListener<T> listener) {
        entityListeners.remove(listener);
    }

    @Override
    public void entityIsValid(T entity) {
        for (ValidatedEntityListener<T> listener : entityListeners) {
            listener.accept(entity);
        }
    }

    @Override
    public void addNewViolation(Violation violation) {
        for (ViolationListener listener : violationListeners) {
            listener.accept(violation);
        }
    }
}
