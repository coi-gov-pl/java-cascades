package pl.gov.coi.cascades.server.presentation.loadtemplate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.gov.coi.cascades.contract.service.WithViolations;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.05.17.
 *
 * View model for loaded template.
 */
class ViewModel extends ResponseEntity<WithViolations<RemoteTemplateSpec>> {

    ViewModel(WithViolations<RemoteTemplateSpec> withViolations, HttpStatus status) {
        super(withViolations, status);
    }

}
