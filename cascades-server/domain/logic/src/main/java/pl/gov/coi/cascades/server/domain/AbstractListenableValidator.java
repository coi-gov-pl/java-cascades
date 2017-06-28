package pl.gov.coi.cascades.server.domain;

import pl.gov.coi.cascades.contract.service.Violation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author agnieszka
 * @since 27.06.17
 */
public abstract class AbstractListenableValidator implements Validator, ViolationListenable {
    private final List<ViolationListener> violationListeners = new ArrayList<>();

    @Override
    public void addViolationListener(ViolationListener listener) {
        violationListeners.add(listener);
    }

    @Override
    public void removeViolationConsumer(ViolationListener listener) {
        violationListeners.remove(listener);
    }

    @Override
    public void addNewViolation(Violation violation) {
        for (ViolationListener listener : violationListeners) {
            listener.accept(violation);
        }
    }
}
