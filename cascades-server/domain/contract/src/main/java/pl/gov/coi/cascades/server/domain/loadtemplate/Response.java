package pl.gov.coi.cascades.server.domain.loadtemplate;

import pl.gov.coi.cascades.contract.service.Violation;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.05.17.
 */
public interface Response {

    /**
     * Method adds error if it occurs during loading template.
     *
     * @param violation Given violation.
     */
    void addViolation(Violation violation);

    /**
     * Method gives an information if loading template completed successfully.
     *
     * @return Information if loading template completed successfully.
     */
    boolean isSuccessful();

    /**
     * A setter for template id.
     *
     * @param id Given id of database.
     */
    void setId(String id);

    /**
     * A setter for template id.
     *
     * @param name Given id of database.
     */
    void setName(String name);

    /**
     * A setter for template status.
     *
     * @param status Given template status.
     */
    void setStatus(String status);

    /**
     * A setter for default template.
     *
     * @param isDefault Given information if template is default.
     */
    void setDefault(boolean isDefault);

    /**
     * A setter for version id.
     *
     * @param versionId Given version of id.
     */
    void setServerId(String versionId);

    /**
     * A setter for version.
     *
     * @param version Given version.
     */
    void setVersion(String version);

}
