package pl.gov.coi.cascades.server.presentation.deletedatabase;

import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.service.CascadesDeleteService;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.deletedatabase.Request;
import pl.gov.coi.cascades.server.domain.deletedatabase.Response;
import pl.gov.coi.cascades.server.domain.deletedatabase.UseCase;
import pl.gov.coi.cascades.server.presentation.UserSession;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.concurrent.Future;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 09.03.17.
 */
public class Controller implements CascadesDeleteService {

    private final UseCase useCase;
    private final UserSession userSession;

    @Inject
    public Controller(UserSession userSession,
                      UseCase useCase) {
        this.userSession = userSession;
        this.useCase = useCase;
    }

    @Override
    public Future<Void> deleteDatabase(@Nullable DatabaseId databaseId) {
        User user = userSession.getSignedInUser();

        Request.RequestBuilder requestBuilder = Request.builder()
            .databaseId(databaseId)
            .user(user);

        Request request = requestBuilder.build();
        Response response = new Response();
        useCase.execute(
            request,
            response
        );

        return null;
    }

}
