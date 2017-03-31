package pl.gov.coi.cascades.junit4;

import com.google.inject.Binder;
import com.google.inject.binder.AnnotatedBindingBuilder;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 29.03.17
 */
public class JUnit4ModuleTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private Binder binder;

    @Mock
    private AnnotatedBindingBuilder<CascadesRuleBuilder> annotatedBindingBuilder;

    @Test
    public void testConfigure() {
        // given
        JUnit4Module jUnit4Module = new JUnit4Module();
        when(binder.bind(any(Class.class)))
            .thenReturn(annotatedBindingBuilder);

        // when
        jUnit4Module.configure(binder);

        // then
        verify(binder)
            .bind(CascadesRuleBuilder.class);
        verify(annotatedBindingBuilder)
            .to(CascadesRuleBuilderImpl.class);
        verifyNoMoreInteractions(binder);
    }

}
