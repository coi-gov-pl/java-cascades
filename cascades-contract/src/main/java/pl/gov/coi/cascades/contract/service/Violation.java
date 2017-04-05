package pl.gov.coi.cascades.contract.service;

/**
 * Violation class for all violations that occur during Cascades operations.
 */
public interface Violation {

    /**
     * Method gives message for error.
     *
     * @return Message for error.
     */
    String getMessage();

    /**
     * Method gives property path.
     *
     * @return Property path.
     */
    String getPropertyPath();

}
