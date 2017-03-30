package pl.gov.coi.cascades.server.persistance.hibernate.development;

import org.springframework.context.SmartLifecycle;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 30.03.17.
 */
interface JpaDevelopmentData extends SmartLifecycle {

    void up();

    void down();

}
