package pl.gov.coi.cascades.server.persistance.hibernate.development.data;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 04.05.17.
 */
public class JpaDevelopmentDataImplTest {

    @Mock
    private UserData userData;

    @Mock
    private DatabaseInstanceData databaseInstanceData;

    @Mock
    private Runnable callback;

    @Mock
    private TemplateIdData templateIdData;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testUp() throws Exception {
        // given
        JpaDevelopmentDataImpl jpaDevelopmentData = new JpaDevelopmentDataImpl(
            userData,
            databaseInstanceData,
            templateIdData
        );

        // when
        jpaDevelopmentData.up();

        // then
        verify(userData, times(1)).up();
        verify(databaseInstanceData, times(1)).up();
        verify(templateIdData, times(1)).up();
    }

    @Test
    public void testDown() throws Exception {
        // given
        JpaDevelopmentDataImpl jpaDevelopmentData = new JpaDevelopmentDataImpl(
            userData,
            databaseInstanceData,
            templateIdData
        );

        // when
        jpaDevelopmentData.down();

        // then
        verify(userData, times(1)).down();
        verify(databaseInstanceData, times(1)).down();
        verify(templateIdData, times(1)).down();
    }

    @Test
    public void testIsAutoStartup() throws Exception {
        // given
        JpaDevelopmentDataImpl jpaDevelopmentData = new JpaDevelopmentDataImpl(
            userData,
            databaseInstanceData,
            templateIdData
        );

        // when
        boolean actual = jpaDevelopmentData.isAutoStartup();

        // then
        assertThat(actual).isTrue();
    }

    @Test
    public void testStop() throws Exception {
        // given
        JpaDevelopmentDataImpl jpaDevelopmentData = new JpaDevelopmentDataImpl(
            userData,
            databaseInstanceData,
            templateIdData
        );

        // when
        jpaDevelopmentData.stop();
    }

    @Test
    public void testStart() throws Exception {
        // given
        JpaDevelopmentDataImpl jpaDevelopmentData = new JpaDevelopmentDataImpl(
            userData,
            databaseInstanceData,
            templateIdData
        );

        // when
        jpaDevelopmentData.start();
    }

    @Test
    public void testStopWithCallback() throws Exception {
        // given
        JpaDevelopmentDataImpl jpaDevelopmentData = new JpaDevelopmentDataImpl(
            userData,
            databaseInstanceData,
            templateIdData
        );

        // when
        jpaDevelopmentData.stop(callback);

        // then
        verify(callback, times(1)).run();
    }

    @Test
    public void testWhenIsNotRunning() throws Exception {
        // given
        JpaDevelopmentDataImpl jpaDevelopmentData = new JpaDevelopmentDataImpl(
            userData,
            databaseInstanceData,
            templateIdData
        );

        // when
        boolean actual = jpaDevelopmentData.isRunning();

        // then
        assertThat(actual).isFalse();
    }

    @Test
    public void testGetPhase() throws Exception {
        // given
        int STOPPED_PHASE = Integer.MAX_VALUE;
        JpaDevelopmentDataImpl jpaDevelopmentData = new JpaDevelopmentDataImpl(
            userData,
            databaseInstanceData,
            templateIdData
        );

        // when
        int actual = jpaDevelopmentData.getPhase();

        // then
        assertThat(actual).isEqualTo(STOPPED_PHASE);
    }

}
