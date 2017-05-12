package pl.gov.coi.cascades.server.presentation.deletedatabase;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.deletedatabase.Request;
import pl.gov.coi.cascades.server.domain.deletedatabase.Response;
import pl.gov.coi.cascades.server.domain.deletedatabase.UseCase;
import pl.gov.coi.cascades.server.presentation.UserSession;

import javax.inject.Inject;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 09.03.17.
 */
@RequestMapping(
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
@Controller
public class DeleteDatabaseController {

    private final UseCase useCase;
    private final UserSession userSession;

    @Inject
    public DeleteDatabaseController(UserSession userSession,
                                    UseCase useCase) {
        this.userSession = userSession;
        this.useCase = useCase;
    }

    @RequestMapping(
        value = "/databases/{id}",
        method = RequestMethod.DELETE
    )
    public ResponseEntity<Response> deleteDatabase(@PathVariable("id") String databaseIdAsString) {
        User user = userSession.getSignedInUser();
        DatabaseId databaseId = new DatabaseId(databaseIdAsString);

        Request.RequestBuilder requestBuilder = Request.builder()
            .databaseId(databaseId)
            .user(user);

        Request request = requestBuilder.build();
        Presenter presenter = new Presenter();
        useCase.execute(
            request,
            presenter
        );

        return presenter.createModel();
    }

}
