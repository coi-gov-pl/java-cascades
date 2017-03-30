package pl.gov.coi.cascades.server.presentation.launchdatabase;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.gov.coi.cascades.contract.service.RemoteDatabaseSpec;
import pl.gov.coi.cascades.contract.service.WithViolations;

/**
 * View model for new database instance.
 */
class ViewModel extends ResponseEntity<WithViolations<RemoteDatabaseSpec>> {

    ViewModel(WithViolations<RemoteDatabaseSpec> withViolations, HttpStatus status) {
        super(withViolations, status);
    }
}
