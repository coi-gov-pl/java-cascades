package pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.database;

import pl.gov.coi.cascades.server.persistance.hibernate.development.DevelopmentBean;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.template.Eaba275Supplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.user.MichaelSupplier;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.*;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.Template;

import java.time.Instant;
import java.util.Date;
import java.util.function.Supplier;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 28.03.17.
 */
@DevelopmentBean
public class Ora12e34Supplier implements DatabaseInstanceSupplier {

    private static final String ORA12E34 = "ora12e34";
    private static final int HTTPS_PORT = 444;
    private static final long ID = 2_143_654_635L;

    @Override
    public DatabaseInstance get() {
        DatabaseInstance db = new DatabaseInstance();
        db.setId(ID);
        db.setType("stub");
        db.setDatabaseName(ORA12E34);
        db.setInstanceName("Oracle is extremely hard");
        db.setNetworkBind(
            NetworkBind.builder()
                .host("cascades.example.org")
                .port(HTTPS_PORT)
                .build()
        );
        db.setCredentials(
            Credentials.builder()
                .username("yjdr6uyjrjt")
                .password("tgjsrt64w6ujstrjrst")
                .build()
        );
        db.setStatus(DatabaseStatus.LAUNCHED);
        db.setReuseTimes(1);
        db.setCreated(Date.from(Instant.parse("2017-03-28T17:56:11.01Z")));
        return db;
    }

    @Override
    public Class<? extends Supplier<User>> getOwnerSupplier() {
        return MichaelSupplier.class;
    }

    @Override
    public Class<? extends Supplier<Template>> getTemplateSupplier() {
        return Eaba275Supplier.class;
    }

}
