package pl.gov.coi.cascades.server.presentation.loadtemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import pl.gov.coi.cascades.contract.domain.TemplateIdStatus;
import pl.gov.coi.cascades.contract.service.Violation;
import pl.gov.coi.cascades.contract.service.WithViolations;
import pl.gov.coi.cascades.server.domain.loadtemplate.Response;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.05.17.
 */
@RequiredArgsConstructor
class Presenter implements Response {

    private String id;
    private String status;
    private boolean isDefault;
    private String versionId;
    private String version;
    private final Collection<Violation> violations = new HashSet<>();

    @Override
    public void addViolation(Violation violation) {
        violations.add(violation);
    }

    @Override
    public boolean isSuccessful() {
        return violations.isEmpty();
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public void setServerId(String versionId) {
        this.versionId = versionId;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Method gives view model of loaded template.
     *
     * @return View model of loaded template.
     */
    ViewModel createModel() {
        return  isSuccessful()
            ? createSuccessfulViewModel()
            : createFailedViewModel();
    }

    private ViewModel createFailedViewModel() {
        WithViolations<RemoteTemplateSpec> withViolations = new WithViolations<>(violations);
        return new ViewModel(withViolations, HttpStatus.BAD_REQUEST);
    }

    private ViewModel createSuccessfulViewModel() {
        RemoteTemplateSpec templateSpec = new RemoteTemplateSpec(
            id,
            status,
            isDefault,
            versionId,
            version
        );
        WithViolations<RemoteTemplateSpec> withViolations = new WithViolations<>(templateSpec);
        return new ViewModel(withViolations, HttpStatus.OK);
    }

}
