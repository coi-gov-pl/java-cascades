package pl.gov.coi.cascades.server.presentation;

import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 02.03.17.
 */
@Component
final class DefaultOptionalMapper implements OptionalMapper {

    @Override
    public <T> Optional<T> toJava8(@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
                                           com.google.common.base.Optional<T> guavaOptional) {
        return Optional.ofNullable(guavaOptional.orNull());
    }

}
