package pl.gov.coi.cascades.server;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.core.Ordered;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.04.17.
 */
public class ApplicationStartListenerTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private ApplicationEnvironmentPreparedEvent applicationEnvironmentPreparedEvent;

    @Test
    public void testGetOrder() throws Exception {
        // given
        int PRIORITY = 5;
        ApplicationStartListener applicationStartListener = new ApplicationStartListener();

        // when
        int actual = applicationStartListener.getOrder();

        // then
        assertThat(actual).isEqualTo(Ordered.HIGHEST_PRECEDENCE + PRIORITY);
    }

}
