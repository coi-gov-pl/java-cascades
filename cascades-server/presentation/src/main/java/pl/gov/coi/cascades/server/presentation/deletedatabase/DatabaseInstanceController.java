package pl.gov.coi.cascades.server.presentation.deletedatabase;

import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.service.CascadesDeleteService;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.deletedatabase.DatabaseInstanceRequest;
import pl.gov.coi.cascades.server.domain.deletedatabase.DatabaseInstanceResponse;
import pl.gov.coi.cascades.server.domain.deletedatabase.DatabaseInstanceUseCase;
import pl.gov.coi.cascades.server.presentation.UserSession;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.concurrent.Future;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 09.03.17.
 */
public class DatabaseInstanceController implements CascadesDeleteService {

    private final DatabaseInstanceUseCase databaseInstanceUseCase;
    private final UserSession userSession;

    @Inject
    public DatabaseInstanceController(UserSession userSession,
                                      DatabaseInstanceUseCase databaseInstanceUseCase) {
        this.userSession = userSession;
        this.databaseInstanceUseCase = databaseInstanceUseCase;
    }

    @Override
    public Future<Void> deleteDatabase(@Nullable DatabaseId databaseId) {
        User user = userSession.getSignedInUser();

        DatabaseInstanceRequest.DatabaseInstanceRequestBuilder requestBuilder = DatabaseInstanceRequest.builder()
            .databaseId(databaseId)
            .user(user);

        DatabaseInstanceRequest databaseInstanceRequest = requestBuilder.build();
        DatabaseInstanceResponse databaseInstanceResponse = new DatabaseInstanceResponse();
        databaseInstanceUseCase.execute(
            databaseInstanceRequest,
            databaseInstanceResponse
        );

        return null;
    }

}
