package contract.service;

import contract.domain.DatabaseId;
import contract.domain.DatabaseType;
import contract.domain.NetworkBind;
import contract.domain.UsernameAndPasswordCredentials;
import lombok.Getter;

import java.net.URL;

@Getter
public class RemoteDatabaseSpec {

	private final DatabaseType type;
	private final DatabaseId id;
	private final String databaseName;
	private final NetworkBind networkBind;
	private final UsernameAndPasswordCredentials credentials;
	private final URL connectionURL;

	public RemoteDatabaseSpec(DatabaseType type,
							  DatabaseId id,
							  String databaseName,
							  NetworkBind networkBind,
							  UsernameAndPasswordCredentials credentials,
							  URL connectionURL) {
		this.type = type;
		this.id = id;
		this.databaseName = databaseName;
		this.networkBind = networkBind;
		this.credentials = credentials;
		this.connectionURL = connectionURL;
	}
}