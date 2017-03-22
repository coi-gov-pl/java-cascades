package pl.gov.coi.cascades.server;

import java.util.Optional;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 16.03.17
 */
public interface OsgiBeanLocator {
    /**
     * Returns an iterable of found implementation of {@link T} type.
     * @param cls a given interface to search
     * @param <T> a type to search
     * @return an iterable of implementation
     */
    <T> Iterable<T> getBeans(Class<T> cls);

    /**
     * Returns a {@link Optional} of search {@link T} type. If implementation was found
     * {@link Optional#of(Object)} will be returned, otherwise {@link Optional#empty()} will be.
     *
     * @param cls a given interface to search
     * @param <T> a type to search
     * @return an optional implementation
     */
    <T> Optional<T> getBean(Class<T> cls);
}
