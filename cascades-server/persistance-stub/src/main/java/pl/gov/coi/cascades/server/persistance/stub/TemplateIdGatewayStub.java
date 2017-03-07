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
    public static final TemplateId templateId1 = new TemplateId("templateId1");
    public static final TemplateId templateId2 = new TemplateId("templateId2");
    public static final TemplateId templateId3 = new TemplateId("templateId3");
    public static final TemplateId templateId4 = new TemplateId("templateId4");

    public TemplateIdGatewayStub() {
        this.templates = new HashMap<>();

        templates.put("templateId1", templateId1);
        templates.put("templateId2", templateId2);
        templates.put("templateId3", templateId3);
        templates.put("templateId4", templateId4);
    }

    @Override
    public Optional<TemplateId> find(String templateId) {
        return Optional.ofNullable(templates.get(templateId));
    }

    public Map<String, TemplateId> getAllTemplates() {
        return templates;
    }

    public void setTemplate(String key, TemplateId template) {
        templates.put(key, template);
    }

    public TemplateId getTemplate(String key) {
        return templates.get(key);
    }

    public void clearTemplates() {
        templates.clear();
    }

    public void removeTemplate(String key) {
        templates.remove(key);
    }

}
