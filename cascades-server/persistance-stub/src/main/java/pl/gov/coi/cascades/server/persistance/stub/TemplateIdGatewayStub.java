package pl.gov.coi.cascades.server.persistance.stub;

import com.google.common.base.Optional;
import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.02.17.
 */
final class TemplateIdGatewayStub implements TemplateIdGateway {

    @Override
    public Optional<TemplateId> find(String templateId) {
        return null;
    }
}
