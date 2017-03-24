package pl.gov.coi.cascades.server.persistance.hibernate.development;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import javax.persistence.EntityManager;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.03.17.
 */
@RequiredArgsConstructor
class JpaDevelopmentData implements DisposableBean {

    private final EntityManager entityManager;
    private UserData userData;

    @EventListener(ContextRefreshedEvent.class)
    void up() {
        getUserData()
            .addJohnRambo();
    }

    void down() {
        getUserData()
            .removeJohnRambo();
    }

    @Override
    public void destroy() throws Exception {
        down();
    }

    private UserData getUserData() {
        if (userData == null) {
            userData = new UserData(entityManager);
        }
        return userData;
    }
}
