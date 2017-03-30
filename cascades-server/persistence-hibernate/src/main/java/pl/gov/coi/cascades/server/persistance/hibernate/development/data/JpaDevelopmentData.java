package pl.gov.coi.cascades.server.persistance.hibernate.development.data;

import org.springframework.context.SmartLifecycle;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 30.03.17.
 */
public interface JpaDevelopmentData extends SmartLifecycle {

    void up();

    void down();

}
