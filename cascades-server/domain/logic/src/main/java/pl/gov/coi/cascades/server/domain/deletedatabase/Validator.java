package pl.gov.coi.cascades.server.domain.deletedatabase;

import lombok.Builder;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.Error;
import pl.gov.coi.cascades.server.domain.ErrorImpl;
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

    private final Response response;
    private final Request request;
    @Nullable
    private final DatabaseId databaseId;
    @Nullable
    private final User user;

    public boolean validate() {
        validateUser();
        validateDatabaseId();
        validateIfDatabaseInstanceBelongsToLoggedUser();
        return response.isSuccessful();
    }

    DatabaseId getDatabaseId() {
        return checkNotNull(databaseId, "20170310:135800");
    }

    User getUser() {
        return checkNotNull(user, "20170310:135820");
    }

    private void validateIfDatabaseInstanceBelongsToLoggedUser() {
        User gotUser = getUser();
        DatabaseId gotDatabaseId = getDatabaseId();
        if (!isDatabaseIdBelongsToLoggedUser(gotUser)) {
            Error errorMessage = new ErrorImpl(
                String.format(
                    "Given id of database: %s doesn't belong to logged user: %s",
                    gotDatabaseId,
                    gotUser
                )
            );

            response.addError(errorMessage);
        }
    }

    private boolean isDatabaseIdBelongsToLoggedUser(User gotUser) {
        for (DatabaseInstance databaseInstance: gotUser.getDatabases()) {
            if (databaseInstance.getDatabaseId().equals(databaseId)) {
                return true;
            }
        }

        return false;
    }

    private void validateDatabaseId() {
        if (!Optional.ofNullable(databaseId).isPresent()) {
            newError("Given database id is not present.");
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
