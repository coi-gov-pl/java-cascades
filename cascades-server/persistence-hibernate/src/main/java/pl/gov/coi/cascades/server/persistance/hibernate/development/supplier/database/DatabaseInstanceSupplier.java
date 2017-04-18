package pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.database;

import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateId;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import java.util.function.Supplier;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 28.03.17.
 */
public interface DatabaseInstanceSupplier extends Supplier<DatabaseInstance> {

    Class<? extends Supplier<User>> getOwnerSupplier();

    Class<? extends Supplier<TemplateId>> getTemplateSupplier();

}
