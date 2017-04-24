package pl.gov.coi.cascades.server;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.slf4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.04.17.
 */
public class DevelopmentConfigurationTest {

    @Mock
    private Logger logger;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testHandle() throws Exception {
        // given
        DevelopmentConfiguration developmentConfiguration = new DevelopmentConfiguration(logger);
        when(logger.isInfoEnabled()).thenReturn(true);

        // when
        developmentConfiguration.handle();

        // then
        verify(logger).info(contains("20170418:134033"));
    }

    @Test
    public void testDefaultConstructor() throws Exception {
        // when
        DevelopmentConfiguration actual = new DevelopmentConfiguration();

        // then
        assertThat(actual).isNotNull();
    }

}
