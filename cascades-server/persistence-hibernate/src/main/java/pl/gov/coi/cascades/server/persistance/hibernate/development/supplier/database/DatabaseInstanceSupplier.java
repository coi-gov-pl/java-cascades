package pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.database;

import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.Template;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import java.util.function.Supplier;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 28.03.17.
 */
public interface DatabaseInstanceSupplier extends Supplier<DatabaseInstance> {

    /**
     * Gets owner supplier.
     *
     * @return Supplier class.
     */
    Class<? extends Supplier<User>> getOwnerSupplier();

    /**
     * Gets template supplier.
     *
     * @return Supplier template.
     */
    Class<? extends Supplier<Template>> getTemplateSupplier();

}
