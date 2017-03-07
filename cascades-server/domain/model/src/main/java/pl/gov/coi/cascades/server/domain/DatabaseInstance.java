package pl.gov.coi.cascades.server.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;

import java.util.Date;

@Builder
@RequiredArgsConstructor
public class DatabaseInstance {

    @Getter
    private final DatabaseId databaseId;
    @Getter
    private final TemplateId templateId;
    @Getter
    private final DatabaseType databaseType;
    @Getter
    private final String instanceName;
    @Getter
    private final int reuseTimes;
    @Getter
    private final String databaseName;
    @Getter
    private final UsernameAndPasswordCredentials credentials;
    @Getter
    private final NetworkBind networkBind;
    @Getter
    private final String status;
    private final Date created;
    private DatabaseInstance databaseInstance;

    /**
     * Copy constructor.
     * @param databaseInstance Given instance of database to copy.
     */
    DatabaseInstance(DatabaseInstance databaseInstance) {
        this.databaseId = databaseInstance.getDatabaseId();
        this.databaseType = databaseInstance.getDatabaseType();
        this.instanceName = databaseInstance.getInstanceName();
        this.templateId = databaseInstance.getTemplateId();
        this.reuseTimes = databaseInstance.getReuseTimes();
        this.databaseName = databaseInstance.getDatabaseName();
        this.credentials = databaseInstance.getCredentials();
        this.networkBind = databaseInstance.getNetworkBind();
        this.status = databaseInstance.getStatus();
        this.created = databaseInstance.getCreated();
    }

    /**
     * Method gives date of database creation.
     *
     * @return Date of database creation.
     */
    public Date getCreated() {
        return Date.class.cast(created.clone());
    }

    /**
     * Setter for network bind.
     *
     * @param networkBind Given network bind.
     * @return Instance of database.
     */
    public DatabaseInstance setNetworkBind(NetworkBind networkBind) {
        DatabaseInstance instance = new DatabaseInstance(databaseInstance);
        instance.setNetworkBind(networkBind);
        return instance;
    }

}
