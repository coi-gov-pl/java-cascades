package pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.database;

import org.junit.Test;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.template.F4ab6a58Supplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.user.MichaelSupplier;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseStatus;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateId;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import java.time.Instant;
import java.util.Date;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.04.17.
 */
public class Pos45y67SupplierTest {

    @Test
    public void testGet() throws Exception {
        // given
        String POS45Y67 = "pos45y67";
        int HTTPS_PORT = 443;
        long ID = 3_084_059_057L;
        String type = "stub";
        String instanceName = "Postgres is *%! hard";
        String host = "cascades.example.org";
        String username = "fddfhy56ytj3";
        String password = "hjsw5y4thaw43t5grw";
        DatabaseStatus status = DatabaseStatus.DELETED;
        Date created = Date.from(Instant.parse("2017-03-28T17:56:11.01Z"));
        Pos45y67Supplier pos45y67Supplier = new Pos45y67Supplier();

        // when
        DatabaseInstance actual = pos45y67Supplier.get();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(ID);
        assertThat(actual.getType()).isEqualTo(type);
        assertThat(actual.getDatabaseName()).isEqualTo(POS45Y67);
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
        Pos45y67Supplier pos45y67Supplier = new Pos45y67Supplier();

        // when
        Class<? extends Supplier<User>> actual = pos45y67Supplier.getOwnerSupplier();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isSameAs(MichaelSupplier.class);
    }

    @Test
    public void testGetTemplateSupplier() throws Exception {
        // given
        Pos45y67Supplier pos45y67Supplier = new Pos45y67Supplier();

        // when
        Class<? extends Supplier<TemplateId>> actual = pos45y67Supplier.getTemplateSupplier();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isSameAs(F4ab6a58Supplier.class);
    }

}
