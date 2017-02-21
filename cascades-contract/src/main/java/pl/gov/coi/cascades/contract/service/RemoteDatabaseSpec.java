package pl.gov.coi.cascades.contract.service;

import lombok.Getter;
import org.slf4j.Logger;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;
import pl.wavesoftware.eid.exceptions.Eid;

import java.net.MalformedURLException;
import java.net.URL;

public class RemoteDatabaseSpec {

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
	@Getter
	private final URL connectionURL;
	private final Logger logger;

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
							  Logger logger,
							  NetworkBind networkBind,
							  UsernameAndPasswordCredentials credentials) {
		this.type = type;
		this.id = id;
		this.databaseName = databaseName;
		this.logger = logger;
		this.networkBind = networkBind;
		this.credentials = credentials;
		this.connectionURL = getAsConnectionURL();
	}

	private URL getAsConnectionURL() {
		URL url = null;
		try {
			url = new URL("http", networkBind.getHost(), networkBind.getPort(), "");
		} catch (MalformedURLException e) {
			logger.error(new Eid("20170221:133843")
					.makeLogMessage(
							e.getMessage()
					)
			);
		}
		return url;
	}

}