package pl.gov.coi.cascades.server.domain.deletedatabase;

import lombok.Builder;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.service.Violation;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.ViolationImpl;
import pl.gov.coi.cascades.server.domain.User;

import javax.annotation.Nullable;
import java.util.Optional;

import static pl.wavesoftware.eid.utils.EidPreconditions.checkNotNull;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 10.03.17.
 */
@Builder
class Validator {

    private static final String PROPERTY_PATH_DATABASE_ID = "databaseId";
    private static final String PROPERTY_PATH_USER = "user";
    private final Response response;
    private final Request request;
    @Nullable
    private final DatabaseId databaseId;
    @Nullable
    private final User user;

    boolean validate() {
        validateUser();
        boolean isDatabaseIdPresent = validateDatabaseId();
        if (isDatabaseIdPresent) {
            validateIfDatabaseInstanceBelongsToLoggedUser();
        }
        return response.isSuccessful();
    }

    User getUser() {
        return checkNotNull(user, "20170310:135820");
    }

    private void validateIfDatabaseInstanceBelongsToLoggedUser() {
        User gotUser = getUser();
        if (!isDatabaseIdBelongsToLoggedUser(gotUser)) {
            Violation violationMessage = new ViolationImpl(
                String.format(
                    "Given id of database doesn't belong to logged user: %s or has been deleted.",
                    gotUser.getUsername()
                ),
                PROPERTY_PATH_DATABASE_ID
            );

            response.addViolation(violationMessage);
        }
    }

    private boolean isDatabaseIdBelongsToLoggedUser(User gotUser) {
        if (gotUser.getDatabases() != null) {
            for (DatabaseInstance databaseInstance : gotUser.getDatabases()) {
                if (databaseInstance.getDatabaseId().equals(databaseId)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean validateDatabaseId() {
        if (!Optional.ofNullable(databaseId).isPresent()) {
            newError(
                PROPERTY_PATH_DATABASE_ID,
                "Given database id is not present."
            );
            return false;
        }
        return true;
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
        response.addViolation(
            new ViolationImpl(
                String.format(message, parameters),
                path
            )
        );
    }

}
