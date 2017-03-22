package pl.gov.coi.cascades.server;

import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationListener;

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

        // then
        assertThat(app)
            .isNotNull()
            .extracting(SpringApplication::getListeners)
            .hasSize(1);
        ApplicationListener<?> listener = app.getListeners()
            .iterator()
            .next();
        assertThat(listener).isInstanceOf(ApplicationStartListener.class);
    }

}
