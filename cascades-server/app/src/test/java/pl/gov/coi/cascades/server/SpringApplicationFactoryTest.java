package pl.gov.coi.cascades.server;

import org.assertj.core.api.Condition;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationListener;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 17.03.17
 */
public class SpringApplicationFactoryTest {
    @Test
    public void testCreate() {
        // given
        SpringApplicationFactory factory = new SpringApplicationFactory();

        // when
        SpringApplication app = factory.create(this.getClass());
        Set<ApplicationListener<?>> listeners = app.getListeners();

        // then
        assertThat(listeners)
            .areExactly(1, new Condition<>(
                applicationListener -> applicationListener instanceof ApplicationStartListener,
                "a subtype of ApplicationStartListener"
            ));
    }

}
