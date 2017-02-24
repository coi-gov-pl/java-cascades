package pl.gov.coi.cascades.server.domain;

/**
 * Error class for all errors which will occur during Cascades operations.
 */
public interface Error {

    /**
     * Method gives message for error.
     * @return Message for error.
     */
	String getMessage();

    /**
     * Method gives property path.
     * @return Property path.
     */
	String getPropertyPath();

}
