package pl.gov.coi.cascades.client.presentation;

import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 30.03.17
 */
public class PresentationModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(CascadesOperationsLogger.class)
            .to(CascadesOperationsLoggerImpl.class);
    }
}
