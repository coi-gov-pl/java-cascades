package pl.gov.coi.cascades.server.persistance.hibernate.development;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.server.persistance.hibernate.development.data.JpaDevelopmentData;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 04.05.17.
 */
public class RefreshDataControllerTest {

    @Mock
    private JpaDevelopmentData jpaDevelopmentData;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testRefresh() throws Exception {
        // given
        RefreshDataController refreshDataController = new RefreshDataController(
            jpaDevelopmentData
        );

        // when
        refreshDataController.refresh();

        // then
        verify(jpaDevelopmentData, times(1)).down();
        verify(jpaDevelopmentData, times(1)).up();
    }

}
