package pl.gov.coi.cascades.server.persistance.hibernate.development;

import lombok.RequiredArgsConstructor;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.Random;

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
        user.setUsername("jrambo" + new Random().nextInt());
        user.setEmail("jrambo@example.org");
        entityManager.persist(user);
        this.jrambo = user;
        return this;
    }

    UserData removeJohnRambo() {
        Optional.ofNullable(jrambo)
            .ifPresent(this::removeUser);
        return this;
    }

    private void removeUser(User user) {
        Long id = user.getId();
        User fetched = entityManager.getReference(User.class, id);
        entityManager.remove(fetched);
    }
}
