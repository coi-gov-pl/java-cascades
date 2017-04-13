package pl.gov.coi.cascades.server.persistance.hibernate.development.data;

import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.03.17.
 */
public class UserData {

    private EntityManager entityManager;
    private Map<Class<Supplier<User>>, User> instances = new HashMap<>();
    private final Iterable<Supplier<User>> supplierList;

    public UserData(Iterable<Supplier<User>> supplierList) {
        this.supplierList = supplierList;
    }

    @PersistenceContext
    void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    Optional<User> getUserForSupplierClass(Class<? extends Supplier<User>> key) {
        return Optional
            .ofNullable(instances.get(key));
    }

    void up() {
        for (Supplier<User> supplier : supplierList) {
            User user = supplier.get();
            entityManager.persist(user);
            instances.put(instancesKey(supplier), user);
        }
    }

    void down() {
        instances.values()
            .forEach(this::removeUser);
        instances.clear();
    }

    @SuppressWarnings("unchecked")
    private static Class<Supplier<User>> instancesKey(Supplier<User> supplier) {
        return (Class<Supplier<User>>) supplier.getClass();
    }

    private void removeUser(User user) {
        Long id = user.getId();
        User fetched = entityManager.find(User.class, id);
        Optional.ofNullable(fetched)
            .ifPresent(entityManager::remove);
    }

}
