package pl.gov.coi.cascades.server.domain.launchdatabase;

import lombok.Builder;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.contract.service.Violation;
import pl.gov.coi.cascades.server.domain.DatabaseLimitGateway;
import pl.gov.coi.cascades.server.domain.DatabaseTypeDTO;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.ViolationImpl;

import javax.annotation.Nullable;
import java.util.Optional;

import static pl.wavesoftware.eid.utils.EidPreconditions.checkNotNull;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.02.17.
 */
@Builder
class Validator {

    private static final String PROPERTY_PATH_USER = "user";
    private static final String PROPERTY_PATH_TEMPLATE_ID = "templateId";
    private static final String PROPERTY_PATH_GLOBAL_LIMIT = "databaseLimit.globalLimit";
    private static final String PROPERTY_PATH_USER_LIMIT = "databaseLimit.userLimit";
    private final Response response;
    private final Request request;
    private final DatabaseLimitGateway databaseLimitGateway;
    private final TemplateIdGateway templateIdGateway;
    private final DatabaseTypeDTO databaseTypeDTO;
    @Nullable
    private TemplateId templateId;
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
            Violation violationMessage = new ViolationImpl(
                String.format(
                    "Global limit of %d launched database instances has been reached",
                    databaseLimitGateway.getGlobalLimit()
                ),
                PROPERTY_PATH_GLOBAL_LIMIT
            );
            response.addError(violationMessage);
        }
        if (databaseLimitGateway.isPersonalLimitExceeded(gotUser)) {
            Violation violationMessage = new ViolationImpl(
                String.format(
                    "Personal limit of %d launched database instances has been reached",
                    databaseLimitGateway.getPersonalLimitPerUser(gotUser)
                ),
                PROPERTY_PATH_USER_LIMIT
            );
            response.addError(violationMessage);
        }
    }

    private void validateTemplateId() {
        Optional<String> input = request.getTemplateId();
        if (!input.isPresent()) {
            if (!templateIdGateway.getDefaultTemplateId().isPresent()) {
                newError(
                    PROPERTY_PATH_TEMPLATE_ID,
                    "Default template id is not present."
                );
            } else {
                templateIdGateway.getDefaultTemplateId().ifPresent(id -> templateId = id);
            }
        } else {
            Optional<TemplateId> found = templateIdGateway.find(input.get());
            if (!found.isPresent()) {
                newError(
                    PROPERTY_PATH_TEMPLATE_ID,
                    "Given template id hasn't been found."
                );
            } else {
                templateId = found.get();
            }
        }
    }

    private void validateUser() {
        if (!Optional.ofNullable(user).isPresent()) {
            newError(
                PROPERTY_PATH_USER,
                "Given user is invalid."
            );
        }
    }

    private void newError(String path, String message, Object... parameters) {
        response.addError(
            new ViolationImpl(
                String.format(message, parameters),
                path
            )
        );
    }
}
