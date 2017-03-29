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
 * @since 28.03.17.
 */
@DevelopmentBean
class Ora12e34Supplier implements DatabaseInstanceSupplier {

    private static final String ORA12E34 = "ora12e34";
    private static final int HTTPS_PORT = 443;

    @Override
    public DatabaseInstance get() {
        DatabaseInstance db = new DatabaseInstance();
        db.setTemplateId("oracle");
        db.setType("stub");
        db.setDatabaseName(ORA12E34);
        db.setInstanceName("Oracle is *%! hard");
        db.setNetworkBind(
            NetworkBind.builder()
                .host("cascades.example.org")
                .port(HTTPS_PORT)
                .build()
        );
        db.setCredentials(
            Credentials.builder()
                .username("xi3jwjxwlq")
                .password("dg344d34d7g3d34")
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

}
