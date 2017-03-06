package pl.gov.coi.cascades.server.persistance.stub;

import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.02.17.
 */
final class TemplateIdGatewayStub implements TemplateIdGateway {

    private Map<String, TemplateId> templates;

    public TemplateIdGatewayStub() {
        this.templates = new HashMap<>();

        TemplateId templateId1 = new TemplateId("abcd1234");
        TemplateId templateId2 = new TemplateId("efgh1234");
        TemplateId templateId3 = new TemplateId("ijkl1234");
        TemplateId templateId4 = new TemplateId("mnou1234");

        templates.put("12345678", templateId1);
        templates.put("23456789", templateId2);
        templates.put("34567891", templateId3);
        templates.put("45678912", templateId4);
    }

    @Override
    public Optional<TemplateId> find(String templateId) {
        return Optional.ofNullable(templates.get(templateId));
    }

    public Map<String, TemplateId> getAllTemplates() {
        return templates;
    }
}
