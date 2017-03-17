package pl.gov.coi.cascades.server.presentation.launchdatabase;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;
import pl.gov.coi.cascades.contract.service.RemoteDatabaseSpec;
import pl.gov.coi.cascades.server.domain.Error;
import pl.gov.coi.cascades.server.domain.launchdatabase.Response;
import pl.gov.coi.cascades.server.presentation.ResponseWrapper;

import java.util.Collection;
import java.util.HashSet;

@RequiredArgsConstructor
class Presenter implements Response {

    private DatabaseId databaseId;
    private NetworkBind networkBind;
    private String databaseName;
    private UsernameAndPasswordCredentials credentials;
    private final Collection<Error> errors = new HashSet<>();

    /**
     * Method gives an information if launching new database instance completed successfully.
     *
     * @return Information if launching new database instance completed successfully.
     */
    @Override
    public boolean isSuccessful() {
        return errors.isEmpty();
    }

    /**
     * Method adds error if it occurs during launching new database instance.
     *
     * @param error Given error.
     */
    @Override
    public void addError(Error error) {
        errors.add(error);
    }

    /**
     * A setter for database id.
     *
     * @param databaseId Given id of database.
     */
    @Override
    public void setDatabaseId(DatabaseId databaseId) {
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

    @Override
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    /**
     * Method gives view model of new database instance.
     *
     * @return View model of new database instance.
     */
    ViewModel createModel() {
        return  isSuccessful()
            ? createSuccessulViewModel()
            : createFailedViewModel();
    }

    private ViewModel createFailedViewModel() {
        ResponseWrapper<RemoteDatabaseSpec> responseWrapper = new ResponseWrapper<>(errors);
        return new ViewModel(responseWrapper, HttpStatus.BAD_REQUEST);
    }

    private ViewModel createSuccessulViewModel() {
        RemoteDatabaseSpec databaseSpec = new RemoteDatabaseSpec(
            databaseId,
            databaseName,
            networkBind,
            credentials
        );
        ResponseWrapper<RemoteDatabaseSpec> responseWrapper = new ResponseWrapper<>(databaseSpec);
        return new ViewModel(responseWrapper, HttpStatus.OK);
    }

}
