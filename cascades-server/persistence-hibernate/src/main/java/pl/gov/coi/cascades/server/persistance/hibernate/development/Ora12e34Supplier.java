package pl.gov.coi.cascades.server.persistance.hibernate.development;

import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseStatus;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import java.time.Instant;
import java.util.Date;
import java.util.function.Supplier;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 28.03.17.
 */
public class Ora12e34Supplier implements DatabaseInstanceSupplier {

    private static final String ORA12E34 = "ora12e34";

    @Override
    public DatabaseInstance get() {
        DatabaseInstance db = new DatabaseInstance();
        db.setDatabaseId(ORA12E34);
        db.setTemplateId("oracle");
        db.setDatabaseType("stub");
        db.setDatabaseName(ORA12E34);
        db.setInstanceName(ORA12E34);
        db.setReuseTimes(1);
        db.setStatus(DatabaseStatus.LAUNCHED);
        db.setCreated(Date.from(Instant.now()));
        return db;
    }

    @Override
    public Class<? extends Supplier<User>> getOwnerSupplier() {
        return JohnRamboSupplier.class;
    }
}
