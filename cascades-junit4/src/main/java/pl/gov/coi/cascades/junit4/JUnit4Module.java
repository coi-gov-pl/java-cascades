package pl.gov.coi.cascades.junit4;

import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 29.03.17
 */
class JUnit4Module implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(CascadesRuleBuilder.class)
            .to(CascadesRuleBuilderImpl.class);
    }
}
