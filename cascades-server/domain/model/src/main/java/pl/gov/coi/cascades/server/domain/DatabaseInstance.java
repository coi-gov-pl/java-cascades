package pl.gov.coi.cascades.server.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;

import javax.xml.crypto.Data;
import java.util.Date;

@ToString
@Builder
@RequiredArgsConstructor
public class DatabaseInstance {

    @Getter
    private final DatabaseId databaseId;
    @Getter
    private final Template template;
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
    private final DatabaseStatus status;
    private final Date created;

    /**
     * Copy constructor.
     *
     * @param databaseInstance Given instance of database to copy.
     */
    public DatabaseInstance(DatabaseInstance databaseInstance) {
        this(databaseInstance.getDatabaseId(),
            databaseInstance.getTemplate(),
            databaseInstance.getDatabaseType(),
            databaseInstance.getInstanceName(),
            databaseInstance.getReuseTimes(),
            databaseInstance.getDatabaseName(),
            databaseInstance.getCredentials(),
            databaseInstance.getNetworkBind(),
            databaseInstance.getStatus(),
            databaseInstance.getCreated()
        );
    }

    /**
     * Copy constructor to set network bind.
     *
     * @param databaseInstance Given instance of database to copy.
     */
    private DatabaseInstance(DatabaseInstance databaseInstance,
                             NetworkBind networkBind) {
        this(databaseInstance.getDatabaseId(),
            databaseInstance.getTemplate(),
            databaseInstance.getDatabaseType(),
            databaseInstance.getInstanceName(),
            databaseInstance.getReuseTimes(),
            databaseInstance.getDatabaseName(),
            databaseInstance.getCredentials(),
            networkBind,
            databaseInstance.getStatus(),
            databaseInstance.getCreated()
        );
    }

    /**
     * Copy constructor to set status of database.
     *
     * @param databaseInstance Given instance of database to copy.
     */
    private DatabaseInstance(DatabaseInstance databaseInstance,
                             DatabaseStatus databaseStatus) {
        this(databaseInstance.getDatabaseId(),
            databaseInstance.getTemplate(),
            databaseInstance.getDatabaseType(),
            databaseInstance.getInstanceName(),
            databaseInstance.getReuseTimes(),
            databaseInstance.getDatabaseName(),
            databaseInstance.getCredentials(),
            databaseInstance.getNetworkBind(),
            databaseStatus,
            databaseInstance.getCreated()
        );
    }

    /**
     * Copy constructor to set database type of database.
     *
     * @param databaseInstance Given instance of database to copy.
     */
    private DatabaseInstance(DatabaseInstance databaseInstance,
                             DatabaseType databaseType) {
        this(databaseInstance.getDatabaseId(),
            databaseInstance.getTemplate(),
            databaseType,
            databaseInstance.getInstanceName(),
            databaseInstance.getReuseTimes(),
            databaseInstance.getDatabaseName(),
            databaseInstance.getCredentials(),
            databaseInstance.getNetworkBind(),
            databaseInstance.getStatus(),
            databaseInstance.getCreated()
        );
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
        return new DatabaseInstance(this, networkBind);
    }

    /**
     * Setter for database type.
     *
     * @param databaseType Given database type.
     * @return Instance of database.
     */
    public DatabaseInstance setDatabaseType(DatabaseType databaseType) {
        return new DatabaseInstance(this, databaseType);
    }

    /**
     * Setter for status of database.
     *
     * @param databaseStatus Given status of database.
     * @return Instance of database.
     */
    public DatabaseInstance setStatus(DatabaseStatus databaseStatus) {
        return new DatabaseInstance(this, databaseStatus);
    }

}
