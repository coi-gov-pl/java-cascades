package pl.gov.coi.cascades.server.presentation.launchdatabase;

import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;
import pl.gov.coi.cascades.contract.service.RemoteDatabaseSpec;

/**
 * View model for new database instance.
 */
public class ViewModel extends RemoteDatabaseSpec {

    /**
     * Required argument constructor.
     *
     * @param id           Given id of template.
     * @param databaseName Given name of database.
     * @param networkBind  Given network bind.
     * @param credentials  Given credentials.
     */
    public ViewModel(DatabaseId id,
                     String databaseName,
                     NetworkBind networkBind,
                     UsernameAndPasswordCredentials credentials) {
        super(id, databaseName, networkBind, credentials);
    }

}
