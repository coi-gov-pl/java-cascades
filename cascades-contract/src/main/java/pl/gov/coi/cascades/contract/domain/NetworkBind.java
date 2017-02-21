package pl.gov.coi.cascades.contract.domain;

public interface NetworkBind {

	/**
	 * Method gives network's host.
	 * @return Network's host.
	 */
	String getHost();

	/**
	 * Method gives network's port.
	 * @return Network's port.
	 */
	int getPort();

}