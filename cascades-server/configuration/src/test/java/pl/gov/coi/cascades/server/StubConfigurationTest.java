package pl.gov.coi.cascades.server;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.slf4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.04.17.
 */
public class StubConfigurationTest {

    @Mock
    private Logger logger;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testDefaultConstructor() {
        // when
        StubConfiguration actual = new StubConfiguration();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testHandle() throws Exception {
        // given
        StubConfiguration stubConfiguration = new StubConfiguration(logger);
        when(logger.isInfoEnabled()).thenReturn(true);

        // when
        stubConfiguration.handle();

        // then
        verify(logger).info(contains("20170419:000235"));
    }

    @Test
    public void testHandleWhenLoggerIsNotEnabled() throws Exception {
        // given
        StubConfiguration stubConfiguration = new StubConfiguration(logger);
        when(logger.isInfoEnabled()).thenReturn(false);

        // when
        stubConfiguration.handle();

        // then
        verify(logger, times(0)).info(anyString());
    }

}
