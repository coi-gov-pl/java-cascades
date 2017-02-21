package pl.gov.coi.cascades.contract.domain;

public interface UsernameAndPasswordCredentials {

	/**
	 * Method gives name of user.
	 * @return Name of user.
	 */
	String getUsername();

	/**
	 * Method gives user's password.
	 * @return User's password.
	 */
	char[] getPassword();

}