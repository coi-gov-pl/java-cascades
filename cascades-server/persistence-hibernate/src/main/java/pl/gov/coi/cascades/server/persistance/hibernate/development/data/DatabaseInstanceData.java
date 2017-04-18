package pl.gov.coi.cascades.server.persistance.hibernate.development.data;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.database.DatabaseInstanceSupplier;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateId;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
public class DatabaseInstanceData {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseInstanceData.class);
    private final List<DatabaseInstance> instances = new ArrayList<>();
    private final Iterable<DatabaseInstanceSupplier> suppliers;
    private EntityManager entityManager;
    private final UserData userData;
    private final TemplateIdData templateIdData;

    @PersistenceContext
    void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    void up() {
        TypedQuery<Long> query =
            entityManager.createQuery(
                "SELECT COUNT(instance.id) FROM DatabaseInstance instance",
                Long.class
            );
        LOGGER.info("Number of Database Instances before adding: {}", query.getSingleResult());
        for (DatabaseInstanceSupplier supplier : suppliers) {
            Class<? extends Supplier<User>> ownerSupplier = supplier.getOwnerSupplier();
            Class<? extends Supplier<TemplateId>> templateSupplier = supplier.getTemplateSupplier();
            Optional<User> userOptional = userData.getUserForSupplierClass(ownerSupplier);
            Optional<TemplateId> templateIdOptional = templateIdData.getTemplateIdForSupplierClass(templateSupplier);
            DatabaseInstance instance = supplier.get();
            userOptional.ifPresent(getUserConsumer(instance));
            templateIdOptional.ifPresent(getTemplateIdConsumer(instance));
        }
        LOGGER.info("Number of Database Instances after adding: {}", query.getSingleResult());
    }

    private Consumer<TemplateId> getTemplateIdConsumer(DatabaseInstance instance) {
        return templateId -> {
            instance.setTemplateId(templateId);
            entityManager.persist(instance);
            instances.add(instance);
        };
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
            DatabaseInstance reference = entityManager.find(instance.getClass(), id);
            Optional.ofNullable(reference)
                .ifPresent(entityManager::remove);
        }
        instances.clear();
    }

}
