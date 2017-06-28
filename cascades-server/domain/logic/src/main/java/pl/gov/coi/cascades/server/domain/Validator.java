package pl.gov.coi.cascades.server.domain;

/**
 * A generic validator interface
 *
 * @author agnieszka
 * @since 27.06.17.
 */
public interface Validator {
    /**
     * Checks a validity of this validator
     * @return true, if valid
     */
    boolean isValid();

}
