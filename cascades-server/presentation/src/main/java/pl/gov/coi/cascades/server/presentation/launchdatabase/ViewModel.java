package pl.gov.coi.cascades.server.presentation.launchdatabase;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.gov.coi.cascades.contract.service.RemoteDatabaseSpec;
import pl.gov.coi.cascades.server.presentation.WithErrors;

/**
 * View model for new database instance.
 */
class ViewModel extends ResponseEntity<WithErrors<RemoteDatabaseSpec>> {

    ViewModel(WithErrors<RemoteDatabaseSpec> withErrors, HttpStatus status) {
        super(withErrors, status);
    }
}
