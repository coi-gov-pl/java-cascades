package pl.gov.coi.cascades.server.presentation.launchdatabase;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.gov.coi.cascades.contract.service.RemoteDatabaseSpec;
import pl.gov.coi.cascades.server.presentation.ResponseWrapper;

/**
 * View model for new database instance.
 */
class ViewModel extends ResponseEntity<ResponseWrapper<RemoteDatabaseSpec>> {

    ViewModel(ResponseWrapper<RemoteDatabaseSpec> responseWrapper, HttpStatus status) {
        super(responseWrapper, status);
    }
}
