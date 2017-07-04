package pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.template;

import org.junit.Test;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.Template;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateIdStatus;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.04.17.
 */
public class F4ab6a58SupplierTest {

    @Test
    public void testGet() throws Exception {
        // given
        TemplateIdStatus status = TemplateIdStatus.CREATED;
        String name = "postgres_template";
        String serverId = "dgrt45gtyt";
        F4ab6a58Supplier f4ab6a58Supplier = new F4ab6a58Supplier();

        // when
        Template actual = f4ab6a58Supplier.get();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getStatus()).isEqualTo(status);
        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.getServerId()).isEqualTo(serverId);
        assertThat(actual.isDefault()).isFalse();
    }

}
