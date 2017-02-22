package pl.gov.coi.cascades.contract.domain;

/**
 * This interface represents a network bind that is a pair of host and port.
 */
public interface NetworkBind {

    /**
     * Method gives network's host.
     *
     * @return Network's host.
     */
    String getHost();

    /**
     * Method gives network's port.
     *
     * @return Network's port.
     */
    int getPort();

}
