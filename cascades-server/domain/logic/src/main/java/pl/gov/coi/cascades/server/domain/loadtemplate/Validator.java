package pl.gov.coi.cascades.server.domain.loadtemplate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import pl.gov.coi.cascades.contract.domain.TemplateIdStatus;
import pl.gov.coi.cascades.server.domain.ViolationImpl;

import static pl.wavesoftware.eid.utils.EidPreconditions.checkNotNull;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.05.17.
 */
@Builder
@AllArgsConstructor
class Validator {

    private final Response response;
    private final Request request;
    private String id;
    private boolean isDefault;
    private String serverId;
    private TemplateIdStatus status;
    private String version;

    public boolean validate() {
        validateZip();
        validateIfCatalogContainsJsonFile();
        validateJsonFileStructure();
        validateScriptsFormat();
        return response.isSuccessful();
    }

    String getId() {
        return checkNotNull(id, "20170524:122357");
    }

    boolean isDefault() {
        return checkNotNull(isDefault, "20170524:122601");
    }

    TemplateIdStatus getStatus() {
        return checkNotNull(status, "20170524:122707");
    }

    String getServerId() {
        return checkNotNull(serverId, "20170524:122732");
    }

    String getVersion() {
        return checkNotNull(version, "20170524:122752");
    }

    private void validateZip() {

    }

    private void validateIfCatalogContainsJsonFile() {

    }

    private void validateJsonFileStructure() {

    }

    private void validateScriptsFormat() {

    }

    private void newError(String path, String message, Object... parameters) {
        response.addViolation(
            new ViolationImpl(
                String.format(message, parameters),
                path
            )
        );
    }

}
