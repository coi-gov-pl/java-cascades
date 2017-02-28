package pl.gov.coi.cascades.server.domain;

import lombok.Getter;
import pl.wavesoftware.eid.utils.EidPreconditions;

import javax.annotation.Nullable;
import java.util.Optional;

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
        this.typeClassName = EidPreconditions.checkNotNull(typeClassName, "20170228:153927");
        this.user = EidPreconditions.checkNotNull(user, "20170228:153954");
        this.templateId = templateId;
        this.instanceName = instanceName;
    }

    /**
     * Gets an optional id of template.
     *
     * @return Optional id of template.
     */
    public Optional<String> getTemplateId() {
        return Optional.ofNullable(templateId);
    }

    /**
     * Gets an instance name of database.
     *
     * @return Optional instance name of database.
     */
    public Optional<String> getInstanceName() {
        return Optional.ofNullable(instanceName);
    }

}
