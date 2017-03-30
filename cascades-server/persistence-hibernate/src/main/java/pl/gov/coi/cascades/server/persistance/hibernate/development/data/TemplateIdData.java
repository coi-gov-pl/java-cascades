package pl.gov.coi.cascades.server.persistance.hibernate.development.data;

import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 30.03.17.
 */
public class TemplateIdData {

    private EntityManager entityManager;
    private Map<Class<Supplier<TemplateId>>, TemplateId> instances = new HashMap<>();
    private final Iterable<Supplier<TemplateId>> supplierList;

    public TemplateIdData(Iterable<Supplier<TemplateId>> supplierList) {
        this.supplierList = supplierList;
    }

    @PersistenceContext
    void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    Optional<TemplateId> getUserForSupplierClass(Class<? extends Supplier<TemplateId>> key) {
        return Optional
            .ofNullable(instances.get(key));
    }

    void up() {
        for (Supplier<TemplateId> supplier : supplierList) {
            TemplateId templateId = supplier.get();
            entityManager.persist(templateId);
            instances.put(instancesKey(supplier), templateId);
        }
    }

    void down() {
        instances.values()
            .forEach(this::removeUser);
        instances.clear();
    }

    @SuppressWarnings("unchecked")
    private static Class<Supplier<TemplateId>> instancesKey(Supplier<TemplateId> supplier) {
        return (Class<Supplier<TemplateId>>) supplier.getClass();
    }

    private void removeUser(TemplateId templateId) {
        Long id = templateId.getId();
        TemplateId fetched = entityManager.getReference(TemplateId.class, id);
        entityManager.remove(fetched);
    }

}
