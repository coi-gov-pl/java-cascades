package pl.gov.coi.cascades.server.persistance.hibernate.development;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.03.17.
 */
@Retention(RUNTIME)
@Qualifier
public @interface DevelopmentBean {
}
