package pl.gov.coi.cascades.server.domain;

import com.google.common.base.Optional;
import lombok.Getter;

import javax.annotation.Nullable;

/**
 * Class for providing request information for launching new database instance.
 */
public class LaunchNewDatabaseInstanceRequest {

    @Getter
    private final String typeClassName;
    @Getter
    private final User user;
    @Nullable
    private final String templateId;
    @Nullable
    private final String instanceName;

    /**
     * Default argument constructor.
     *
     * @param typeClassName Name of type class.
     * @param user          User of the database.
     * @param templateId    Given id of template (Optional).
     * @param instanceName  Given name of database instance (Optional).
     */
    public LaunchNewDatabaseInstanceRequest(String typeClassName,
                                            User user,
                                            @Nullable String templateId,
                                            @Nullable String instanceName) {
        this.typeClassName = typeClassName;
        this.user = user;
        this.templateId = templateId;
        this.instanceName = instanceName;
    }

    /**
     * Gets an optional id of template.
     *
     * @return Optional id of template.
     */
    public Optional<String> getTemplateId() {
        return Optional.fromNullable(templateId);
    }

    /**
     * Gets an instance name of database.
     *
     * @return Optional instance name of database.
     */
    public Optional<String> getInstanceName() {
        return Optional.fromNullable(instanceName);
    }

}
