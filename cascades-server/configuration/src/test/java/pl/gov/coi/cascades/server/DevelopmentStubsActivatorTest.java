package pl.gov.coi.cascades.server;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.server.persistance.stub.DatabaseTypeStub;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.04.17.
 */
public class DevelopmentStubsActivatorTest {

    @Mock
    private Logger logger;

    @Mock
    private BundleContext bundleContext;

    @Mock
    private ServiceRegistration<DatabaseType> databaseTypeRegistration;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testStopWhenLoggerIsInfoEnabled() throws Exception {
        // given
        int INFO_INVOKING_TIMES = 2;
        int UNREGISTER_INVOKING_TIMES = 1;
        DevelopmentStubsActivator developmentStubsActivator = new DevelopmentStubsActivator(
            logger
        );
        when(logger.isInfoEnabled()).thenReturn(true);
        when(bundleContext.registerService(
            eq(DatabaseType.class),
            any(DatabaseTypeStub.class),
            any())
        ).thenReturn(databaseTypeRegistration);
        developmentStubsActivator.start(bundleContext);

        // when
        developmentStubsActivator.stop(bundleContext);

        // then
        verify(logger).info(contains("20170418:134847"));
        verify(logger).info(contains("Stopping OSGi bundle"));
        verify(logger, times(INFO_INVOKING_TIMES)).info(anyString());
        verify(databaseTypeRegistration, times(UNREGISTER_INVOKING_TIMES)).unregister();
    }

    @Test
    public void testStopWhenLoggerIsNotInfoEnabled() throws Exception {
        // given
        int INFO_INVOKING_TIMES = 0;
        int UNREGISTER_INVOKING_TIMES = 1;
        DevelopmentStubsActivator developmentStubsActivator = new DevelopmentStubsActivator(
            logger
        );
        when(logger.isInfoEnabled()).thenReturn(false);
        when(bundleContext.registerService(
            eq(DatabaseType.class),
            any(DatabaseTypeStub.class),
            any())
        ).thenReturn(databaseTypeRegistration);
        developmentStubsActivator.start(bundleContext);

        // when
        developmentStubsActivator.stop(bundleContext);

        // then
        verify(logger, times(INFO_INVOKING_TIMES)).info(anyString());
        verify(databaseTypeRegistration, times(UNREGISTER_INVOKING_TIMES)).unregister();
    }

    @Test
    public void testDefaultConstructor() {
        // when
        DevelopmentStubsActivator developmentStubsActivator = new DevelopmentStubsActivator();

        // then
        assertThat(developmentStubsActivator).isNotNull();
    }

    @Test
    public void testStartWhenLoggerIsInfoEnabled() throws Exception {
        // given
        DevelopmentStubsActivator developmentStubsActivator = new DevelopmentStubsActivator(
            logger
        );
        when(logger.isInfoEnabled()).thenReturn(true);

        // when
        developmentStubsActivator.start(bundleContext);

        // then
        verify(logger).info(contains("20170418:134715"));
        verify(logger).info(contains("Staring OSGi bundle"));
    }

    @Test
    public void testStartWhenLoggerIsNotInfoEnabled() throws Exception {
        // given
        DevelopmentStubsActivator developmentStubsActivator = new DevelopmentStubsActivator(
            logger
        );

        // when
        developmentStubsActivator.start(bundleContext);

        // then
        verify(logger, times(0)).info(anyString());
    }

}
