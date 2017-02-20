package contract.service;

import contract.domain.DatabaseId;

import java.util.concurrent.Future;

public interface CascadesService {

	/**
	 * 
	 * @param request
	 */
	Future<RemoteDatabaseSpec> launchDatabase(RemoteDatabaseRequest request);

	/**
	 * 
	 * @param databaseId
	 */
	Future<Void> deleteDatabase(DatabaseId databaseId);

}