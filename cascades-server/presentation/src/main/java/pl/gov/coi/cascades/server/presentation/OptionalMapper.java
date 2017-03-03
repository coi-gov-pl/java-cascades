package pl.gov.coi.cascades.server.presentation;

import java.util.Optional;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 02.03.17.
 */
@FunctionalInterface
interface OptionalMapper {

    /**
     * Converts a Guava Optional to Java 8 Optional class
     *
     * @param <T> a type of optional referenced object
     * @param guavaOptional a guava optional
     * @return a java 8 optional
     */
    <T> Optional<T> toJava8(@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
                                com.google.common.base.Optional<T> guavaOptional);

}
