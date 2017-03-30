package pl.gov.coi.cascades.client.configuration;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;
import org.assertj.core.api.Condition;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;

import javax.inject.Inject;
import javax.inject.Named;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.gov.coi.cascades.client.configuration.Container.INSTANCE;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 29.03.17
 */
public class ContainerTest {
    private final Module foobarStringModule = new Module() {
        @Override
        public void configure(Binder binder) {
            binder.bind(String.class)
                .annotatedWith(Names.named("foo"))
                .toInstance("bar");
            binder.bind(Example.class).to(ExampleImpl.class);
        }
    };

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    @After
    public void cleanup() {
        INSTANCE.reset();
    }

    @Test
    public void testRegister() {
        // when
        Container ref = INSTANCE.register(foobarStringModule);

        // then
        assertThat(INSTANCE).isNotNull();
        assertThat(ref).isSameAs(INSTANCE);
    }

    @Test
    public void testRegisterThrowing() {
        // given
        String bean = INSTANCE.getBean(String.class);

        // then
        assertThat(bean).isEmpty();
        thrown.expect(EidIllegalStateException.class);
        thrown.expectMessage("20170329:105518");

        // when
        INSTANCE.register(foobarStringModule);
    }

    @Test
    public void testGetBean() {
        // given
        INSTANCE.register(foobarStringModule);
        String desc = "injected with @Named('foo') testString that equals to 'bar'";
        Condition<Example> cond = new Condition<Example>(desc) {
            @Override
            public boolean matches(Example example) {
                return example.getTestString().equals("bar");
            }
        };

        // when
        Example example = INSTANCE.getBean(Example.class);

        // then
        assertThat(example)
            .isNotNull()
            .is(cond);

        // when
        Example example2 = INSTANCE.getBean(Example.class);

        // then
        assertThat(example2)
            .isNotNull()
            .isNotSameAs(example)
            .is(cond);
    }

    private interface Example {
        String getTestString();
    }

    private static final class ExampleImpl implements Example {
        private final String testString;

        @Inject
        private ExampleImpl(@Named("foo") String testString) {
            this.testString = testString;
        }

        @Override
        public String getTestString() {
            return testString;
        }
    }

}
