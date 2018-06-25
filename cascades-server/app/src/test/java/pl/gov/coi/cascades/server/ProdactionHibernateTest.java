package pl.gov.coi.cascades.server;

import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Łukasz Małek <lukasz.malek@coi.gov.pl>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles({Environment.PRODUCTION_NAME, ProfileType.HIBERNATE_NAME})
@interface ProductionHibernateTest {
}
