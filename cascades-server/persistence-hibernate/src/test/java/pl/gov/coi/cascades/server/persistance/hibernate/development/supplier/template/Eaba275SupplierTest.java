package pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.template;

import org.junit.Test;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.Template;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.Template;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateIdStatus;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.04.17.
 */
public class Eaba275SupplierTest {

    @Test
    public void testGet() throws Exception {
        // given
        TemplateIdStatus status = TemplateIdStatus.CREATED;
        String name = "oracle_template";
        String serverId = "rgey65getg";
        Eaba275Supplier eaba275Supplier = new Eaba275Supplier();

        // when
        Template actual = eaba275Supplier.get();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getStatus()).isEqualTo(status);
        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.getServerId()).isEqualTo(serverId);
        assertThat(actual.isDefault()).isTrue();
    }

}
