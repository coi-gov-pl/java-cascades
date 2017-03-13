package pl.gov.coi.cascades.server.domain.launchdatabase;

import lombok.Builder;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.server.domain.DatabaseLimitGateway;
import pl.gov.coi.cascades.server.domain.DatabaseTypeDTO;
import pl.gov.coi.cascades.server.domain.Error;
import pl.gov.coi.cascades.server.domain.ErrorImpl;
import pl.gov.coi.cascades.server.domain.User;

import javax.annotation.Nullable;
import java.util.Optional;

import static pl.wavesoftware.eid.utils.EidPreconditions.checkNotNull;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.02.17.
 */
@Builder
class Validator {

    private final Response response;
    private final Request request;
    private final DatabaseLimitGateway databaseLimitGateway;
    private final DatabaseTypeDTO databaseTypeDTO;
    @Nullable
    private final TemplateId templateId;
    @Nullable
    private final User user;
    private DatabaseType databaseType;

    public boolean validate() {
        validateTemplateId();
        validateUser();
        validateLimitOfDatabases();
        validateDatabaseType();
        return response.isSuccessful();
    }

    TemplateId getTemplateId() {
        return checkNotNull(templateId, "20170228:163337");
    }

    DatabaseType getDatabaseType() {
        return checkNotNull(databaseType, "20170228:163428");
    }

    User getUser() {
        return checkNotNull(user, "20170228:164936");
    }

    private void validateDatabaseType() {
        databaseTypeDTO.onFail(response::addError)
            .onSuccess(databaseType1 -> {
                Validator.this.setDatabaseType(databaseType1);
                response.setDatabaseType(databaseType1);
            })
            .resolve();
    }

    private void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }

    private void validateLimitOfDatabases() {
        User gotUser = getUser();
        if (databaseLimitGateway.isGlobalLimitExceeded()) {
            Error errorMessage = new ErrorImpl(
                String.format(
                    "Global limit of %d launched database instances has been reached",
                    databaseLimitGateway.getGlobalLimit()
                )
            );
            response.addError(errorMessage);
        }
        if (databaseLimitGateway.isPersonalLimitExceeded(gotUser)) {
            Error errorMessage = new ErrorImpl(
                String.format(
                    "Personal limit of %d launched database instances has been reached",
                    databaseLimitGateway.getPersonalLimitPerUser(gotUser)
                )
            );
            response.addError(errorMessage);
        }
    }

    private void validateTemplateId() {
        if (!Optional.ofNullable(templateId).isPresent()) {
            newError("Given template id is not present.");
        }
    }

    private void validateUser() {
        if (!Optional.ofNullable(user).isPresent()) {
            newError("Given user is invalid.");
        }
    }

    private void newError(String message, Object... parameters) {
        response.addError(new ErrorImpl(
            String.format(message, parameters)
        ));
    }
}
