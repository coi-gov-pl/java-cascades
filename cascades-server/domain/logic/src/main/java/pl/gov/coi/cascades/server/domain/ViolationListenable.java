package pl.gov.coi.cascades.server.domain;

import pl.gov.coi.cascades.contract.service.Violation;

import java.util.function.Consumer;

/**
 * @author agnieszka
 * @since 27.06.17
 */
public interface ViolationListenable {

    /**
     * Adds a violation listener
     * @param listener a violation listener
     */
    void addViolationListener(ViolationListener listener);

    /**
     * Removes a violation listener
     * @param listener a violation listener
     */
    void removeViolationConsumer(ViolationListener listener);

    /**
     * Adds a new violation
     * @param violation a violation
     */
    void addNewViolation(Violation violation);

    /**
     * A Violation listener
     */
    interface ViolationListener extends Consumer<Violation> {

    }
}
