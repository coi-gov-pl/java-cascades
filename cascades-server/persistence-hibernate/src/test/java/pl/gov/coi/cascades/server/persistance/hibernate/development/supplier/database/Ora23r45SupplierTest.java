package pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.database;

import org.junit.Test;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.template.F4ab6a58Supplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.user.MichaelSupplier;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseStatus;
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
public class Ora23r45SupplierTest {

    @Test
    public void testGet() throws Exception {
        // given
        String ORA23R45 = "ora23r45";
        int HTTPS_PORT = 443;
        long ID = 48_279_590_549L;
        String type = "stub";
        String instanceName = "Oracle is *%! hard";
        String host = "cascades.example.org";
        String username = "dje4njknjkrn";
        String password = "vfnui34jnghie";
        DatabaseStatus status = DatabaseStatus.LAUNCHED;
        Date created = Date.from(Instant.parse("2017-03-28T17:56:11.01Z"));
        Ora23r45Supplier ora23r45Supplier = new Ora23r45Supplier();

        // when
        DatabaseInstance actual = ora23r45Supplier.get();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(ID);
        assertThat(actual.getType()).isEqualTo(type);
        assertThat(actual.getDatabaseName()).isEqualTo(ORA23R45);
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
        Ora23r45Supplier ora23r45Supplier = new Ora23r45Supplier();

        // when
        Class<? extends Supplier<User>> actual = ora23r45Supplier.getOwnerSupplier();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isSameAs(MichaelSupplier.class);
    }

    @Test
    public void testGetTemplateSupplier() throws Exception {
        // given
        Ora23r45Supplier ora23r45Supplier = new Ora23r45Supplier();

        // when
        Class<? extends Supplier<Template>> actual = ora23r45Supplier.getTemplateSupplier();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isSameAs(F4ab6a58Supplier.class);
    }

}
