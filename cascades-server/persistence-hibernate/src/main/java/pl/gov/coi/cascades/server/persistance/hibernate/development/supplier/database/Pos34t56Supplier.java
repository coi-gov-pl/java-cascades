package pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.database;

import pl.gov.coi.cascades.server.persistance.hibernate.development.DevelopmentBean;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.user.JohnRamboSupplier;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.Credentials;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseStatus;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.NetworkBind;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateId;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateIdStatus;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import java.time.Instant;
import java.util.Date;
import java.util.function.Supplier;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 31.03.17.
 */
@DevelopmentBean
public class Pos34t56Supplier implements DatabaseInstanceSupplier {

    private static final String POS34T56 = "pos34t56";
    private static final int HTTPS_PORT = 443;

    @Override
    public DatabaseInstance get() {
        DatabaseInstance db = new DatabaseInstance();
        TemplateId templateId = new TemplateId();
        templateId.setServerId("ukdtksu6w66j");
        templateId.setDefault(true);
        templateId.setStatus(TemplateIdStatus.CREATED);
        db.setTemplateId(templateId);
        db.setId(6854362462L);
        db.setType("stub");
        db.setDatabaseName(POS34T56);
        db.setInstanceName("Postgres is *%! hard");
        db.setNetworkBind(
            NetworkBind.builder()
                .host("cascades.example.org")
                .port(HTTPS_PORT)
                .build()
        );
        db.setCredentials(
            Credentials.builder()
                .username("fdrg5yh545y")
                .password("5h5h54jyuyr7za")
                .build()
        );
        db.setReuseTimes(1);
        db.setStatus(DatabaseStatus.LAUNCHED);
        db.setCreated(Date.from(Instant.parse("2017-03-28T17:56:11.01Z")));
        return db;
    }

    @Override
    public Class<? extends Supplier<User>> getOwnerSupplier() {
        return JohnRamboSupplier.class;
    }

}
