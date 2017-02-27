package pl.gov.coi.cascades.server.domain;

import com.google.common.base.Optional;
import pl.gov.coi.cascades.contract.domain.TemplateId;

import javax.annotation.Nullable;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.02.17.
 */
public class LaunchNewDatabaseInstanceValidator {

    private final LaunchNewDatabaseInstanceResponse response;
    @Nullable
    private final TemplateId templateId;
    @Nullable
    private final User user;

    public LaunchNewDatabaseInstanceValidator(LaunchNewDatabaseInstanceResponse response,
                                              @Nullable TemplateId templateId,
                                              @Nullable User user) {
        this.response = response;
        this.templateId = templateId;
        this.user = user;
    }

    public void validate() {
        validateTemplateId();
        validateUser();
    }

    private void validateTemplateId() {
        if (!getTemplateId().isPresent()) {
            Error error = new ErrorImpl("Given template id is not present");
            response.addError(error);
        }
    }

    private void validateUser() {
        if (!getUser().isPresent()) {
            Error error = new ErrorImpl("Given user is invalid");
            response.addError(error);
        }
    }

    private Optional<TemplateId> getTemplateId() {
        return Optional.fromNullable(templateId);
    }

    private Optional<User> getUser() {
        return Optional.fromNullable(user);
    }

}
