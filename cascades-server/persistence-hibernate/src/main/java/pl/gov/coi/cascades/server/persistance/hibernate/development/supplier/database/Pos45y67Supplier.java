package pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.database;

import pl.gov.coi.cascades.server.persistance.hibernate.development.DevelopmentBean;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.template.F4ab6a58Supplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.user.MichaelSupplier;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.Credentials;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseStatus;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.NetworkBind;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateId;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import java.time.Instant;
import java.util.Date;
import java.util.function.Supplier;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 29.03.17.
 */
@DevelopmentBean
public class Pos45y67Supplier implements DatabaseInstanceSupplier {

    private static final String POS45Y67 = "pos45y67";
    private static final int HTTPS_PORT = 443;
    private static final long ID = 3_084_059_057L;

    @Override
    public DatabaseInstance get() {
        DatabaseInstance db = new DatabaseInstance();
        db.setId(ID);
        db.setType("stub");
        db.setDatabaseName(POS45Y67);
        db.setInstanceName("Postgres is *%! hard");
        db.setNetworkBind(
            NetworkBind.builder()
                .host("cascades.example.org")
                .port(HTTPS_PORT)
                .build()
        );
        db.setCredentials(
            Credentials.builder()
                .username("fddfhy56ytj3")
                .password("hjsw5y4thaw43t5grw")
                .build()
        );
        db.setReuseTimes(1);
        db.setStatus(DatabaseStatus.DELETED);
        db.setCreated(Date.from(Instant.parse("2017-03-28T17:56:11.01Z")));
        return db;
    }

    @Override
    public Class<? extends Supplier<User>> getOwnerSupplier() {
        return MichaelSupplier.class;
    }

    @Override
    public Class<? extends Supplier<TemplateId>> getTemplateSupplier() {
        return F4ab6a58Supplier.class;
    }

}
