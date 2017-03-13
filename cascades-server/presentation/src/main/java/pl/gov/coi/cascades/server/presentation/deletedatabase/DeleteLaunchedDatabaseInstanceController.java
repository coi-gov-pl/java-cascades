package pl.gov.coi.cascades.server.presentation.deletedatabase;

import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.service.CascadesDeleteService;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.deletedatabase.DeleteLaunchedDatabaseInstanceRequest;
import pl.gov.coi.cascades.server.domain.deletedatabase.DeleteLaunchedDatabaseInstanceResponse;
import pl.gov.coi.cascades.server.domain.deletedatabase.DeleteLaunchedDatabaseInstanceUseCase;
import pl.gov.coi.cascades.server.presentation.UserSession;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.concurrent.Future;

import static pl.gov.coi.cascades.server.domain.deletedatabase.DeleteLaunchedDatabaseInstanceRequest.*;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 09.03.17.
 */
public class DeleteLaunchedDatabaseInstanceController implements CascadesDeleteService {

    private final DeleteLaunchedDatabaseInstanceUseCase deleteLaunchedDatabaseInstanceUseCase;
    private final UserSession userSession;

    @Inject
    public DeleteLaunchedDatabaseInstanceController(UserSession userSession,
                                                    DeleteLaunchedDatabaseInstanceUseCase deleteLaunchedDatabaseInstanceUseCase) {
        this.userSession = userSession;
        this.deleteLaunchedDatabaseInstanceUseCase = deleteLaunchedDatabaseInstanceUseCase;
    }

    @Override
    public Future<Void> deleteDatabase(@Nullable DatabaseId databaseId) {
        User user = userSession.getSignedInUser();

        DeleteLaunchedDatabaseInstanceRequestBuilder requestBuilder = builder()
            .databaseId(databaseId)
            .user(user);

        DeleteLaunchedDatabaseInstanceRequest deleteLaunchedDatabaseInstanceRequest = requestBuilder.build();
        DeleteLaunchedDatabaseInstanceResponse deleteLaunchedDatabaseInstanceResponse = new DeleteLaunchedDatabaseInstanceResponse();
        deleteLaunchedDatabaseInstanceUseCase.execute(
            deleteLaunchedDatabaseInstanceRequest,
            deleteLaunchedDatabaseInstanceResponse
        );

        return null;
    }

}
