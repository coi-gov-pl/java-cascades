package pl.gov.coi.cascades.server.presentation.launchdatabase;

import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;
import pl.gov.coi.cascades.contract.service.RemoteDatabaseSpec;

/**
 * View model for new database instance.
 */
public class DatabaseInstanceViewModel extends RemoteDatabaseSpec {

    /**
     * Required argument constructor.
     *
     * @param type         Given type of database.
     * @param id           Given id of template.
     * @param databaseName Given name of database.
     * @param networkBind  Given network bind.
     * @param credentials  Given credentials.
     */
    public DatabaseInstanceViewModel(DatabaseType type,
                                     DatabaseId id,
                                     String databaseName,
                                     NetworkBind networkBind,
                                     UsernameAndPasswordCredentials credentials) {
        super(type, id, databaseName, networkBind, credentials);
    }
}
