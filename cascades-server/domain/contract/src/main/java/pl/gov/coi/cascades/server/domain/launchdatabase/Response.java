package pl.gov.coi.cascades.server.domain.launchdatabase;

import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;
import pl.gov.coi.cascades.server.domain.Error;

public interface Response {

    /**
     * Method gives an information if launching new database instance completed successfully.
     *
     * @return Information if launching new database instance completed successfully.
     */
    boolean isSuccessful();

    /**
     * Method adds error if it occurs during launching new database instance.
     *
     * @param error Given error.
     */
    void addError(Error error);

    /**
     * A setter for database id.
     *
     * @param databaseId Given id of database.
     */
    void setDatabaseId(DatabaseId databaseId);

    /**
     * A setter for database type.
     *
     * @param databaseType Given type of database.
     */
    void setDatabaseType(DatabaseType databaseType);

    /**
     * A setter for network bind.
     *
     * @param networkBind Given network bind.
     */
    void setNetworkBind(NetworkBind networkBind);

    /**
     * A setter for credentials.
     *
     * @param credentials Given credentials.
     */
    void setCredentials(UsernameAndPasswordCredentials credentials);

}
