package pl.gov.coi.cascades.server.persistance.stub;

import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.contract.domain.TemplateIdStatus;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.02.17.
 */
public final class TemplateIdGatewayStub implements TemplateIdGateway {

    public static final TemplateId TEMPLATE_ID1 = new TemplateId(
        "templateId1",
        TemplateIdStatus.CREATED,
        false,
        "nv485587vmv89"
    );
    public static final TemplateId TEMPLATE_ID2 = new TemplateId(
        "templateId2",
        TemplateIdStatus.CREATED,
        false,
        "v589m69b968"
    );
    public static final TemplateId TEMPLATE_ID3 = new TemplateId(
        "templateId3",
        TemplateIdStatus.CREATED,
        true,
        "mjtjbyu968y96"
    );
    public static final TemplateId TEMPLATE_ID4 = new TemplateId(
        "templateId4",
        TemplateIdStatus.CREATED,
        false,
        "y6v96u8869m3tg"
    );
    private Map<String, TemplateId> templates;

    public TemplateIdGatewayStub() {
        this.templates = new HashMap<>();

        setTemplate(TEMPLATE_ID1);
        setTemplate(TEMPLATE_ID2);
        setTemplate(TEMPLATE_ID3);
        setTemplate(TEMPLATE_ID4);
    }

    @Override
    public Optional<TemplateId> find(String templateId) {
        return Optional.ofNullable(templates.get(templateId));
    }

    @Override
    public Optional<TemplateId> getDefaultTemplateId() {
        return Optional.of(TEMPLATE_ID3);
    }

    public Map<String, TemplateId> getAllTemplates() {
        return templates;
    }

    public void setTemplate(TemplateId template) {
        templates.put(template.getId(), template);
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
