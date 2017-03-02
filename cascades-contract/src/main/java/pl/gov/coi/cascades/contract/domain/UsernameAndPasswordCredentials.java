package pl.gov.coi.cascades.contract.domain;

import java.io.Serializable;

/**
 * This interface represents credentials in form of username and password
 */
public interface UsernameAndPasswordCredentials extends Serializable {

    /**
     * Method gives name of user.
     *
     * @return Name of user.
     */
    String getUsername();

    /**
     * Method gives user's password.
     *
     * @return User's password.
     */
    char[] getPassword();

}
