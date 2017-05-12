package pl.gov.coi.cascades.server;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.04.17.
 */
public class ApplicationStartListenerTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private ApplicationEnvironmentPreparedEvent event;

    @Mock
    private ConfigurableEnvironment environment;

    @Test
    public void testOnApplicationEvent() {
        // given
        when(event.getEnvironment()).thenReturn(environment);
        ApplicationStartListener applicationStartListener = new ApplicationStartListener();

        // when
        applicationStartListener.onApplicationEvent(event);

        // then
        verify(environment, times(1)).setDefaultProfiles(anyString(), anyString());
    }

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
