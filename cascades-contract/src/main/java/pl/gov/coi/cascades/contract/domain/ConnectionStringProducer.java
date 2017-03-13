package pl.gov.coi.cascades.contract.domain;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 22.02.17
 */
public interface ConnectionStringProducer {
    /**
     * Will produce a valid connection URL based on given parameters
     * @param databaseNetworkBind a database network bind
     * @param databaseName a name of the database
     * @return a valid connection string
     */
    String produce(NetworkBind databaseNetworkBind,
                String databaseName);
}
