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
public class Eaba275Supplier implements Supplier<Template> {

    @Override
    public Template get() {
        Template template = new Template();
        template.setStatus(TemplateIdStatus.CREATED);
        template.setGeneratedId("sdfasdq1234");
        template.setName("oracle_template");
        template.setServerId("rgey65getg");
        template.setDefault(true);
        template.setVersion("0.0.1");
        return template;
    }

}
