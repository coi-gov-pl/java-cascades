package pl.gov.coi.cascades.server.presentation.deletedatabase;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.gov.coi.cascades.server.domain.deletedatabase.Response;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 22.03.17.
 */
public class ViewModel extends ResponseEntity<Response> {

    public ViewModel(Response body, HttpStatus status) {
        super(body, status);
    }

}
