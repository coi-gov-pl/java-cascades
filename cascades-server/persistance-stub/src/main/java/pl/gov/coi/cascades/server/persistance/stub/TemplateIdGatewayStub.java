package pl.gov.coi.cascades.server.persistance.stub;

import pl.gov.coi.cascades.contract.domain.Template;
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

    private static final String VERSION = "0.0.1";

    public static final Template TEMPLATE_ID1 = new Template(
        "oracle_template",
        "hv5sj5mb",
        TemplateIdStatus.CREATED,
        false,
        "nv485587vmv89",
        VERSION
    );
    public static final Template TEMPLATE_ID2 = new Template(
        "templateId2",
        "hstrn5k7",
        TemplateIdStatus.CREATED,
        false,
        "v589m69b968",
        VERSION
    );
    public static final Template TEMPLATE_ID3 = new Template(
        "templateId3",
        "gv62mbka",
        TemplateIdStatus.CREATED,
        true,
        "mjtjbyu968y96",
        VERSION
    );
    public static final Template TEMPLATE_ID4 = new Template(
        "templateId4",
        "budjw2m7",
        TemplateIdStatus.CREATED,
        false,
        "y6v96u8869m3tg",
        VERSION
    );
    private static Map<String, Template> templates = new HashMap<>();

    public TemplateIdGatewayStub() {
        setTemplate(TEMPLATE_ID1);
        setTemplate(TEMPLATE_ID2);
        setTemplate(TEMPLATE_ID3);
        setTemplate(TEMPLATE_ID4);
    }

    @Override
    public Optional<Template> find(String templateId) {
        return Optional.ofNullable(templates.get(templateId));
    }

    @Override
    public Optional<Template> getDefaultTemplateId() {
        for (Map.Entry<String, Template> entry : templates.entrySet()) {
            if (entry.getValue().isDefault()) {
                return Optional.of(entry.getValue());
            }
        }
        return Optional.empty();
    }

    @Override
    public void addTemplate(Template template) {
        setTemplate(template);
    }

    public static Map<String, Template> getAllTemplates() {
        return templates;
    }

    public void setTemplate(Template template) {
        templates.put(template.getId(), template);
    }

    public Template getTemplate(String key) {
        return templates.get(key);
    }

    public void clearTemplates() {
        templates.clear();
    }

    public void removeTemplate(String key) {
        templates.remove(key);
    }

}
