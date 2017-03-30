package pl.gov.coi.cascades.server.persistance.hibernate.development.data;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.transaction.Transactional;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.03.17.
 */
@Singleton
@Transactional
@RequiredArgsConstructor
public class JpaDevelopmentDataImpl implements JpaDevelopmentData {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpaDevelopmentDataImpl.class);
    private final UserData userData;
    private final DatabaseInstanceData databaseInstanceData;
    private Status status = Status.REMOVED;

    @Override
    public void up() {
        changeStatus(Status.PERSISTING);
        userData.up();
        databaseInstanceData.up();
        changeStatus(Status.PERSISTED);
    }

    @Override
    public void down() {
        changeStatus(Status.REMOVING);
        databaseInstanceData.down();
        userData.down();
        changeStatus(Status.REMOVED);
    }

    private void changeStatus(Status status) {
        LOGGER.info("Changing status from {} to {}", this.status, status);
        this.status = status;
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
        return status == Status.PERSISTED;
    }

    @Override
    public int getPhase() {
        return status.ordinal();
    }

    private enum Status {
        REMOVED,
        PERSISTING,
        PERSISTED,
        REMOVING
    }

}
