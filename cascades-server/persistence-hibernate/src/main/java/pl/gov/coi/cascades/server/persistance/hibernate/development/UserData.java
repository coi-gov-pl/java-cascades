package pl.gov.coi.cascades.server.persistance.hibernate.development;

import lombok.RequiredArgsConstructor;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.03.17.
 */
@RequiredArgsConstructor
class UserData {
    private final EntityManager entityManager;
    private final DatabaseInstanceData databaseInstanceData =
        new DatabaseInstanceData();
    @Nullable
    private User jrambo;

    UserData addJohnRambo() {
        User user = new User();
        user.setUsername("jrambo");
        user.setEmail("jrambo@example.org");
        entityManager.persist(user);
        this.jrambo = user;
        return this;
    }

    UserData removeJohnRambo() {
        Optional.ofNullable(jrambo)
            .ifPresent(entityManager::remove);
        return this;
    }
}
