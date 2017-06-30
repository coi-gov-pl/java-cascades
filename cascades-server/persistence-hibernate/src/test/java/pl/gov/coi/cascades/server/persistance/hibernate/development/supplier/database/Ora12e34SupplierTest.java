package pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.database;

import org.junit.Test;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.template.Eaba275Supplier;
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
public class Ora12e34SupplierTest {

    @Test
    public void testGet() throws Exception {
        // given
        String ORA12E34 = "ora12e34";
        int HTTPS_PORT = 444;
        long ID = 2_143_654_635L;
        String type = "stub";
        String instanceName = "Oracle is extremely hard";
        String host = "cascades.example.org";
        String username = "yjdr6uyjrjt";
        String password = "tgjsrt64w6ujstrjrst";
        DatabaseStatus status = DatabaseStatus.LAUNCHED;
        Date created = Date.from(Instant.parse("2017-03-28T17:56:11.01Z"));
        Ora12e34Supplier ora12e34Supplier = new Ora12e34Supplier();

        // when
        DatabaseInstance actual = ora12e34Supplier.get();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(ID);
        assertThat(actual.getType()).isEqualTo(type);
        assertThat(actual.getDatabaseName()).isEqualTo(ORA12E34);
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
        Ora12e34Supplier ora12e34Supplier = new Ora12e34Supplier();

        // when
        Class<? extends Supplier<User>> actual = ora12e34Supplier.getOwnerSupplier();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isSameAs(MichaelSupplier.class);
    }

    @Test
    public void testGetTemplateSupplier() throws Exception {
        // given
        Ora12e34Supplier ora12e34Supplier = new Ora12e34Supplier();

        // when
        Class<? extends Supplier<Template>> actual = ora12e34Supplier.getTemplateSupplier();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isSameAs(Eaba275Supplier.class);
    }

}
