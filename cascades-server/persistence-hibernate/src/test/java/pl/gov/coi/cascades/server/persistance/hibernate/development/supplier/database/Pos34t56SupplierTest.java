package pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.database;

import org.junit.Test;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.template.Eaba275Supplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.user.JohnRamboSupplier;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseStatus;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.Template;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.Template;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import java.time.Instant;
import java.util.Date;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.04.17.
 */
public class Pos34t56SupplierTest {

    @Test
    public void testGet() throws Exception {
        // given
        String POS34T56 = "pos34t56";
        int HTTPS_PORT = 443;
        long ID = 6_854_362_462L;
        String type = "stub";
        String instanceName = "Postgres is *%! hard";
        String host = "cascades.example.org";
        String username = "fdrg5yh545y";
        String password = "5h5h54jyuyr7za";
        DatabaseStatus status = DatabaseStatus.LAUNCHED;
        Date created = Date.from(Instant.parse("2017-03-28T17:56:11.01Z"));
        Pos34t56Supplier pos34t56Supplier = new Pos34t56Supplier();

        // when
        DatabaseInstance actual = pos34t56Supplier.get();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(ID);
        assertThat(actual.getType()).isEqualTo(type);
        assertThat(actual.getDatabaseName()).isEqualTo(POS34T56);
        assertThat(actual.getInstanceName()).isEqualTo(instanceName);
        assertThat(actual.getNetworkBind()).isNotNull();
        assertThat(actual.getNetworkBind().getHost()).isEqualTo(host);
        assertThat(actual.getNetworkBind().getPort()).isEqualTo(HTTPS_PORT);
        assertThat(actual.getCredentials()).isNotNull();
        assertThat(actual.getCredentials().getPassword()).isEqualTo(password);
        assertThat(actual.getCredentials().getUsername()).isEqualTo(username);
        assertThat(actual.getStatus()).isEqualTo(status);
        assertThat(actual.getReuseTimes()).isEqualTo(1);
        assertThat(actual.getCreated()).isEqualTo(created);
    }

    @Test
    public void testGetOwnerSupplier() throws Exception {
        // given
        Pos34t56Supplier pos34t56Supplier = new Pos34t56Supplier();

        // when
        Class<? extends Supplier<User>> actual = pos34t56Supplier.getOwnerSupplier();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isSameAs(JohnRamboSupplier.class);
    }

    @Test
    public void testGetTemplateSupplier() throws Exception {
        // given
        Pos34t56Supplier pos34t56Supplier = new Pos34t56Supplier();

        // when
        Class<? extends Supplier<Template>> actual = pos34t56Supplier.getTemplateSupplier();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isSameAs(Eaba275Supplier.class);
    }

}
