package pl.gov.coi.cascades.server.persistance.hibernate.development.data;

import pl.gov.coi.cascades.server.persistance.hibernate.entity.Template;

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
    private Map<Class<Supplier<Template>>, Template> instances = new HashMap<>();
    private final Iterable<Supplier<Template>> supplierList;

    public TemplateIdData(Iterable<Supplier<Template>> supplierList) {
        this.supplierList = supplierList;
    }

    @PersistenceContext
    void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    Optional<Template> getTemplateIdForSupplierClass(Class<? extends Supplier<Template>> key) {
        return Optional
            .ofNullable(instances.get(key));
    }

    void up() {
        for (Supplier<Template> supplier : supplierList) {
            Template template = supplier.get();
            entityManager.persist(template);
            instances.put(instancesKey(supplier), template);
        }
    }

    void down() {
        instances.values()
            .forEach(this::removeTemplateId);
        instances.clear();
    }

    @SuppressWarnings("unchecked")
    private static Class<Supplier<Template>> instancesKey(Supplier<Template> supplier) {
        return (Class<Supplier<Template>>) supplier.getClass();
    }

    private void removeTemplateId(Template template) {
        Long id = template.getId();
        Template fetched = entityManager.find(Template.class, id);
        Optional.ofNullable(fetched)
            .ifPresent(entityManager::remove);
    }

}
