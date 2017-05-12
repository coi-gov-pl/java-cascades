package pl.gov.coi.cascades.server;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.osgi.framework.launch.Framework;
import org.slf4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.04.17.
 */
public class OsgiContainerTest {

    private static final int STOPPED_PHASE = 3;
    @Mock
    private Framework framework;

    @Mock
    private Runnable callback;

    @Mock
    private Logger logger;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testIsAutoStartup() throws Exception {
        // given
        OsgiContainer osgiContainer = new OsgiContainer(framework);

        // when
        boolean actual = osgiContainer.isAutoStartup();

        // then
        assertThat(actual).isTrue();
    }

    @Test
    public void testStopWithCallbackWhenLoggerIsInfoEnabled() throws Exception {
        // given
        when(logger.isInfoEnabled()).thenReturn(true);
        OsgiContainer osgiContainer = new OsgiContainer(
            logger,
            framework
        );

        // when
        osgiContainer.stop(callback);

        // then
        verify(callback, times(1)).run();
        verify(logger).info(contains("20170418:235353"));
    }

    @Test
    public void testStopWithCallbackWhenLoggerIsNotInfoEnabled() throws Exception {
        // given
        when(logger.isInfoEnabled()).thenReturn(false);
        OsgiContainer osgiContainer = new OsgiContainer(
            logger,
            framework
        );

        // when
        osgiContainer.stop(callback);

        // then
        verify(callback, times(1)).run();
    }

    @Test
    public void testStart() throws Exception {
        // given
        OsgiContainer osgiContainer = new OsgiContainer(framework);

        // when
        osgiContainer.start();
    }

    @Test
    public void testStop() throws Exception {
        // given
        OsgiContainer osgiContainer = new OsgiContainer(framework);

        // when
        osgiContainer.stop();
    }

    @Test
    public void testWhenIsNotRunning() throws Exception {
        // given
        OsgiContainer osgiContainer = new OsgiContainer(framework);

        // when
        boolean actual = osgiContainer.isRunning();

        // then
        assertThat(actual).isFalse();
    }

    @Test
    public void testGetPhase() throws Exception {
        // given
        OsgiContainer osgiContainer = new OsgiContainer(framework);

        // when
        int actual = osgiContainer.getPhase();

        // then
        assertThat(actual).isEqualTo(STOPPED_PHASE);
    }

    @Test
    public void testProvideOsgiBeanLocator() throws Exception {
        // given
        OsgiContainer osgiContainer = new OsgiContainer(framework);

        // when
        OsgiBeanLocator actual = osgiContainer.provideOsgiBeanLocator();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isInstanceOf(OsgiBeanLocatorImpl.class);
    }

}
