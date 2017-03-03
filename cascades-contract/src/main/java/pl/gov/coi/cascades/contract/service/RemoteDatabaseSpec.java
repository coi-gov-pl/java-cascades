package pl.gov.coi.cascades.contract.service;

import lombok.Getter;
import pl.gov.coi.cascades.contract.domain.ConnectionStringProducer;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;

import java.io.Serializable;

/**
 * Remote database instance specification describes a created remote database connection parameters
 */
public class RemoteDatabaseSpec implements Serializable {

    private static final long serialVersionUID = 42L;

	@Getter
	private final DatabaseType type;
	@Getter
	private final DatabaseId id;
	@Getter
	private final String databaseName;
	@Getter
	private final NetworkBind networkBind;
	@Getter
	private final UsernameAndPasswordCredentials credentials;

	/**
	 * Required argument constructor.
	 * @param type Given type of database.
	 * @param id Given id of template.
	 * @param databaseName Given name of database.
	 * @param networkBind Given network bind.
	 * @param credentials Given credentials.
	 */
	public RemoteDatabaseSpec(DatabaseType type,
							  DatabaseId id,
							  String databaseName,
							  NetworkBind networkBind,
							  UsernameAndPasswordCredentials credentials) {
		this.type = type;
		this.id = id;
		this.databaseName = databaseName;
		this.networkBind = networkBind;
		this.credentials = credentials;
	}

    /**
     * Returns a valid connection string to be used in JDBC
     * @return a valid connection string
     */
	public String getConnectionString() {
        ConnectionStringProducer producer = type.getConnectionStringProducer();
        return producer.produce(networkBind, databaseName);
	}

}
