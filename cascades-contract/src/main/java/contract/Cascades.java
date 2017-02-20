package contract;

import contract.service.RemoteDatabaseSpec;

public interface Cascades {

	void createDatabase();

	void removeDatabase();

	boolean isCreated();

	RemoteDatabaseSpec getSpec();

}