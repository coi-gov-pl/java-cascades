package pl.gov.coi.cascades.contract.configuration;

import com.google.common.base.Optional;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.Template;
import lombok.Getter;

import javax.annotation.Nullable;

/**
 * This class represents a database driver configuration
 */
public class Driver {

    @Getter
    private final DatabaseType type;
    private final Template template;

    /**
     * Required argument constructor.
     *
     * @param type       Given type of driver.
     * @param template Given id of template of driver.
     */
    public Driver(DatabaseType type,
                  @Nullable Template template) {
        this.type = type;
        this.template = template;
    }

    /**
     * Gets an optional id of template.
     *
     * @return Optional id of template.
     */
    public Optional<Template> getTemplateId() {
        return Optional.fromNullable(template);
    }

}
