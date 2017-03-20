package pl.gov.coi.cascades.server.presentation.deletedatabase;

import org.springframework.http.HttpStatus;
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

import javax.annotation.Nullable;
import javax.inject.Inject;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 09.03.17.
 */
@RequestMapping(
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE
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
    public ResponseEntity<Response> deleteDatabase(@Nullable @PathVariable("id") String databaseIdAsString) {
        User user = userSession.getSignedInUser();
        DatabaseId databaseId = new DatabaseId(databaseIdAsString);

        Request.RequestBuilder requestBuilder = Request.builder()
            .databaseId(databaseId)
            .user(user);

        Request request = requestBuilder.build();
        Response response = new Response();
        useCase.execute(
            request,
            response
        );

        return getResponseMessage(response);
    }

    private ResponseEntity<Response> getResponseMessage(Response response) {
        return response.isSuccessful()
            ? ResponseEntity.status(HttpStatus.OK).body(response)
            : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
