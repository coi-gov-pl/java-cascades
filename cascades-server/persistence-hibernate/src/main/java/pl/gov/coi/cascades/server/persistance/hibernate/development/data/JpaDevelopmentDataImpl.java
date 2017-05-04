package pl.gov.coi.cascades.server.persistance.hibernate.development.data;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;
import pl.wavesoftware.eid.exceptions.Eid;

import javax.inject.Singleton;
import javax.transaction.Transactional;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.03.17.
 */
@Singleton
@Transactional
@RequiredArgsConstructor
public class JpaDevelopmentDataImpl implements JpaDevelopmentData, SmartLifecycle {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpaDevelopmentDataImpl.class);
    private final UserData userData;
    private final DatabaseInstanceData databaseInstanceData;
    private final TemplateIdData templateIdData;
    private Status status = Status.REMOVED;

    @Override
    public void up() {
        changeStatus(Status.PERSISTING);
        templateIdData.up();
        userData.up();
        databaseInstanceData.up();
        changeStatus(Status.PERSISTED);
    }

    @Override
    public void down() {
        changeStatus(Status.REMOVING);
        databaseInstanceData.down();
        userData.down();
        templateIdData.down();
        changeStatus(Status.REMOVED);
    }

    private void changeStatus(Status status) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(new Eid("20170419:000835").makeLogMessage(
                "Changing status from %s to %s",
                this.status,
                status
            ));
        }
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
        return Integer.MAX_VALUE;
    }

    private enum Status {
        REMOVED,
        PERSISTING,
        PERSISTED,
        REMOVING
    }

}
