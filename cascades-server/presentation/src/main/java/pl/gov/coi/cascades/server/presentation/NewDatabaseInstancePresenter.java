package pl.gov.coi.cascades.server.presentation;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;
import pl.gov.coi.cascades.server.domain.contract.Error;
import pl.gov.coi.cascades.server.domain.contract.LaunchNewDatabaseInstanceResponse;

@RequiredArgsConstructor
public class NewDatabaseInstancePresenter implements LaunchNewDatabaseInstanceResponse {

    @Setter
    private String databaseId;
    @Setter
    private DatabaseType databaseType;
    @Setter
    private NetworkBind networkBind;
    @Setter
    private UsernameAndPasswordCredentials credentials;

    /**
     * Method gives an information if launching new database instance completed successfully.
     *
     * @return Information if launching new database instance completed successfully.
     */
    @Override
    public boolean isSuccessful() {
        return false;
    }

    /**
     * Method adds error if it occurs during launching new database instance.
     *
     * @param error Given error.
     */
    @Override
    public void addError(Error error) {

    }

    /**
     * A setter for database id.
     *
     * @param databaseId Given id of database.
     */
    @Override
    public void setDatabaseId(String databaseId) {
        this.databaseId = databaseId;
    }

    /**
     * A setter for database type.
     *
     * @param databaseType Given type of database.
     */
    @Override
    public void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }

    /**
     * A setter for network bind.
     *
     * @param networkBind Given network bind.
     */
    @Override
    public void setNetworkBind(NetworkBind networkBind) {
        this.networkBind = networkBind;
    }

    /**
     * A setter for credentials.
     *
     * @param credentials Given credentials.
     */
    @Override
    public void setCredentials(UsernameAndPasswordCredentials credentials) {
        this.credentials = credentials;
    }

    /**
     * Method gives view model of new database instance.
     *
     * @return View model of new database instance.
     */
    public NewDatabaseInstanceViewModel createModel() {
        throw new UnsupportedOperationException();
    }

}
