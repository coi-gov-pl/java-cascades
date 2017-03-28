package pl.gov.coi.cascades.server.persistance.hibernate.development;

import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import java.util.function.Supplier;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 28.03.17.
 */
interface DatabaseInstanceSupplier extends Supplier<DatabaseInstance> {

    Class<? extends Supplier<User>> getOwnerSupplier();

}
