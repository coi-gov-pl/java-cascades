package pl.gov.coi.cascades.server.persistance.hibernate.development;

import pl.gov.coi.cascades.server.persistance.hibernate.entity.Credentials;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseStatus;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.NetworkBind;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import java.time.Instant;
import java.util.Date;
import java.util.function.Supplier;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 29.03.17.
 */
@DevelopmentBean
class Pos45y67Supplier implements DatabaseInstanceSupplier {

    private static final String POS45Y67 = "pos45y67";
    private static final int HTTPS_PORT = 443;

    @Override
    public DatabaseInstance get() {
        DatabaseInstance db = new DatabaseInstance();
        db.setTemplateId("postgres");
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
}
