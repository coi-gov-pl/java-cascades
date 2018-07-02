package pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.template;

import pl.gov.coi.cascades.server.persistance.hibernate.development.DevelopmentBean;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.Template;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateIdStatus;

import java.util.function.Supplier;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 30.03.17.
 */
@DevelopmentBean
public class F4ab6a58Supplier implements Supplier<Template> {

    @Override
    public Template get() {
        Template template = new Template();
        template.setStatus(TemplateIdStatus.CREATED);
        template.setGeneratedId("dasda34");
        template.setName("postgres_template");
        template.setServerId("dgrt45gtyt");
        template.setDefault(false);
        template.setVersion("0.0.1");
        return template;
    }

}
