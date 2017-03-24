package pl.gov.coi.cascades.server.persistance.hibernate.development;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.03.17.
 */
@Singleton
@Transactional
@RequiredArgsConstructor
class JpaDevelopmentData implements SmartLifecycle {

    private Logger logger = LoggerFactory.getLogger(JpaDevelopmentData.class);
    private EntityManager entityManager;
    private UserData userData;
    private Status status = Status.STOPPED;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void up() {
        changeStatus(Status.STARTING);
        getUserData()
            .addJohnRambo();
        changeStatus(Status.STARTED);
    }

    public void down() {
        changeStatus(Status.STOPPING);
        getUserData()
            .removeJohnRambo();
        changeStatus(Status.STOPPED);
    }

    private void changeStatus(Status status) {
        logger.info("Changing status from {} to {}", this.status, status);
        this.status = status;
    }

    private UserData getUserData() {
        if (userData == null) {
            userData = new UserData(entityManager);
        }
        return userData;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback) {
        down();
        callback.run();
    }

    @Override
    public void start() {
        up();
    }

    @Override
    public void stop() {
        // do nothing
    }

    @Override
    public boolean isRunning() {
        return status == Status.STARTED;
    }

    @Override
    public int getPhase() {
        return status.ordinal();
    }

    private enum Status {
        STOPPED,
        STARTING,
        STARTED,
        STOPPING
    }
}
