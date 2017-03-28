package pl.gov.coi.cascades.server.persistance.hibernate.development;

import lombok.RequiredArgsConstructor;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.03.17.
 */
@RequiredArgsConstructor
class DatabaseInstanceData {

    private final List<DatabaseInstance> instances = new ArrayList<>();
    private final Iterable<DatabaseInstanceSupplier> suppliers;
    private EntityManager entityManager;
    private final UserData userData;

    @PersistenceContext
    void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    void up() {
        for (DatabaseInstanceSupplier supplier : suppliers) {
            Class<? extends Supplier<User>> ownerSupplier = supplier.getOwnerSupplier();
            Optional<User> userOptional = userData.getUserForSupplierClass(ownerSupplier);
            DatabaseInstance instance = supplier.get();
            userOptional.ifPresent(getUserConsumer(instance));
        }
    }

    private Consumer<User> getUserConsumer(DatabaseInstance instance) {
        return user -> {
            user.addDatabase(instance);
            entityManager.persist(instance);
            instances.add(instance);
        };
    }

    void down() {
        for (DatabaseInstance instance : instances) {
            Long id = instance.getId();
            DatabaseInstance reference = entityManager.getReference(instance.getClass(), id);
            entityManager.remove(reference);
        }
        instances.clear();
    }



}
