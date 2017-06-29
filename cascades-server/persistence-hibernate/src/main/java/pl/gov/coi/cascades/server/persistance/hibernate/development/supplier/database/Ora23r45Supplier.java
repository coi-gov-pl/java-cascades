package pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.database;

import pl.gov.coi.cascades.server.persistance.hibernate.development.DevelopmentBean;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.template.F4ab6a58Supplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.user.MichaelSupplier;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.*;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.Template;

import java.time.Instant;
import java.util.Date;
import java.util.function.Supplier;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 29.03.17.
 */
@DevelopmentBean
public class Ora23r45Supplier implements DatabaseInstanceSupplier {

    private static final String ORA23R45 = "ora23r45";
    private static final int HTTPS_PORT = 443;
    private static final long ID = 48_279_590_549L;

    @Override
    public DatabaseInstance get() {
        DatabaseInstance db = new DatabaseInstance();
        db.setId(ID);
        db.setType("stub");
        db.setDatabaseName(ORA23R45);
        db.setInstanceName("Oracle is *%! hard");
        db.setNetworkBind(
            NetworkBind.builder()
                .host("cascades.example.org")
                .port(HTTPS_PORT)
                .build()
        );
        db.setCredentials(
            Credentials.builder()
                .username("dje4njknjkrn")
                .password("vfnui34jnghie")
                .build()
        );
        db.setReuseTimes(1);
        db.setStatus(DatabaseStatus.LAUNCHED);
        db.setCreated(Date.from(Instant.parse("2017-03-28T17:56:11.01Z")));
        return db;
    }

    @Override
    public Class<? extends Supplier<User>> getOwnerSupplier() {
        return MichaelSupplier.class;
    }

    @Override
    public Class<? extends Supplier<Template>> getTemplateSupplier() {
        return F4ab6a58Supplier.class;
    }

}
