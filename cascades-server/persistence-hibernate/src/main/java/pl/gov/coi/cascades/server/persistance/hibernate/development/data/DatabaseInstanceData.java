package pl.gov.coi.cascades.server.persistance.hibernate.development.data;

import com.google.common.annotations.VisibleForTesting;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.database.DatabaseInstanceSupplier;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateId;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;
import pl.wavesoftware.eid.exceptions.Eid;

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
public class DatabaseInstanceData {

    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(DatabaseInstanceData.class);
    private final List<DatabaseInstance> instances = new ArrayList<>();
    private final Iterable<DatabaseInstanceSupplier> suppliers;
    private EntityManager entityManager;
    private final UserData userData;
    private final TemplateIdData templateIdData;
    private final Logger logger;

    public DatabaseInstanceData(Iterable<DatabaseInstanceSupplier> suppliers,
                                UserData userData,
                                TemplateIdData templateIdData) {
        this(
            DEFAULT_LOGGER,
            suppliers,
            userData,
            templateIdData
        );
    }

    @VisibleForTesting
    DatabaseInstanceData(Logger logger,
                         Iterable<DatabaseInstanceSupplier> suppliers,
                         UserData userData,
                         TemplateIdData templateIdData) {
        this.suppliers = suppliers;
        this.userData = userData;
        this.templateIdData = templateIdData;
        this.logger = logger;
    }

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
        if (logger.isInfoEnabled()) {
            logger.info(new Eid("20170419:000515").makeLogMessage(
                "Number of Database Instances before adding: %s",
                query.getSingleResult()
            ));
        }
        for (DatabaseInstanceSupplier supplier : suppliers) {
            Class<? extends Supplier<User>> ownerSupplier = supplier.getOwnerSupplier();
            Class<? extends Supplier<TemplateId>> templateSupplier = supplier.getTemplateSupplier();
            Optional<User> userOptional = userData.getUserForSupplierClass(ownerSupplier);
            Optional<TemplateId> templateIdOptional = templateIdData.getTemplateIdForSupplierClass(templateSupplier);
            DatabaseInstance instance = supplier.get();
            userOptional.ifPresent(getUserConsumer(instance));
            templateIdOptional.ifPresent(getTemplateIdConsumer(instance));
        }
        if (logger.isInfoEnabled()) {
            logger.info(new Eid("20170419:000641").makeLogMessage(
                "Number of Database Instances after adding: %s",
                query.getSingleResult()
            ));
        }
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
