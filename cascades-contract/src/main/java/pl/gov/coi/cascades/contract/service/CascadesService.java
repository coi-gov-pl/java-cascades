package pl.gov.coi.cascades.contract.service;

import pl.gov.coi.cascades.contract.domain.DatabaseId;

import java.util.concurrent.Future;

public interface CascadesService {

	/**
	 * Method gives future of specification of remote database for given request.
	 * @param request Given request.
	 */
	Future<RemoteDatabaseSpec> launchDatabase(RemoteDatabaseRequest request);

	/**
	 * Method gives future for given id of database.
	 * @param databaseId Given id of database.
	 */
	Future<Void> deleteDatabase(DatabaseId databaseId);

}