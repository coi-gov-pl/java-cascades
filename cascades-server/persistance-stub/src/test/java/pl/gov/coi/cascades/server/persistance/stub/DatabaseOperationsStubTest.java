package pl.gov.coi.cascades.server.persistance.stub;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.slf4j.Logger;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
public class DatabaseOperationsStubTest {

    private DatabaseOperationsStub databaseOperationsStub;

    @Mock
    private Logger logger;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void init() {
        databaseOperationsStub = new DatabaseOperationsStub(
            logger
        );
    }

    @Test
    public void shouldCreateDatabase() {
        // given
        when(logger.isInfoEnabled()).thenReturn(true);

        // when
        DatabaseInstance result = databaseOperationsStub.createDatabase(
            DatabaseIdGatewayStub.INSTANCE1
        );

        // then
        assertThat(result).isNotNull();
        assertNotNull(result.getNetworkBind());
        assertThat(result).isNotEqualTo(DatabaseIdGatewayStub.INSTANCE1);
        verify(logger).info(contains("20180628:181922"));
        verify(logger).info(contains("Database has been created."));
    }

    @Test
    public void shouldCreateDatabaseWhenLoggerIsNotInfoEnabled() throws Exception {
        //given
        when(logger.isInfoEnabled()).thenReturn(false);

        //when
        DatabaseInstance result = databaseOperationsStub.createDatabase(
            DatabaseIdGatewayStub.INSTANCE1
        );

        //then
        assertThat(result).isNotNull();
        assertThat(result).isNotEqualTo(DatabaseIdGatewayStub.INSTANCE1);
        verify(logger, times(0)).info(anyString());
    }

    @Test
    public void shouldDeleteDatabase() {
        // given
        when(logger.isInfoEnabled()).thenReturn(true);

        // when
        databaseOperationsStub.deleteDatabase(
            DatabaseIdGatewayStub.INSTANCE1
        );

        // then
        verify(logger).info(contains("20180628:182022"));
        verify(logger).info(contains("Database has been deleted."));
    }

    @Test
    public void shouldDeleteDatabaseWhenLoggerIsNotInfoEnabled() throws Exception {
        when(logger.isInfoEnabled()).thenReturn(false);

        // when
        databaseOperationsStub.deleteDatabase(
            DatabaseIdGatewayStub.INSTANCE1
        );

        // then
        verify(logger, times(0)).info(anyString());
    }
}
