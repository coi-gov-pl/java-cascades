package pl.gov.coi.cascades.server.persistance.stub;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.slf4j.Logger;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;

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
public class DatabaseInstanceGatewayStubTest {

    @Mock
    private Logger logger;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testLaunchDatabase() throws Exception {
        // given
        DatabaseInstanceGatewayStub databaseInstanceGatewayStub = new DatabaseInstanceGatewayStub(
            logger
        );
        when(logger.isInfoEnabled()).thenReturn(true);

        // when
        DatabaseInstance actual = databaseInstanceGatewayStub.save(
            DatabaseIdGatewayStub.INSTANCE1
        );

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isNotEqualTo(DatabaseIdGatewayStub.INSTANCE1);
        verify(logger).info(contains("20170419:001122"));
        verify(logger).info(contains("Database has been launched."));
    }

    @Test
    public void testLaunchDatabaseWhenLoggerIsNotInfoEnabled() throws Exception {
        // given
        DatabaseInstanceGatewayStub databaseInstanceGatewayStub = new DatabaseInstanceGatewayStub(
            logger
        );
        when(logger.isInfoEnabled()).thenReturn(false);

        // when
        DatabaseInstance actual = databaseInstanceGatewayStub.save(
            DatabaseIdGatewayStub.INSTANCE1
        );

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isNotEqualTo(DatabaseIdGatewayStub.INSTANCE1);
        verify(logger, times(0)).info(anyString());
    }

    @Test
    public void testDeleteDatabase() throws Exception {
        // given
        DatabaseInstanceGatewayStub databaseInstanceGatewayStub = new DatabaseInstanceGatewayStub(
            logger
        );
        when(logger.isInfoEnabled()).thenReturn(true);

        // when
        databaseInstanceGatewayStub.deleteDatabase(
            DatabaseIdGatewayStub.INSTANCE1
        );

        // then
        verify(logger).info(contains("20170419:001226"));
        verify(logger).info(contains("Database has been deleted."));
    }

    @Test
    public void testDeleteDatabaseWhenLoggerIsNotInfoEnabled() throws Exception {
        // given
        DatabaseInstanceGatewayStub databaseInstanceGatewayStub = new DatabaseInstanceGatewayStub(
            logger
        );
        when(logger.isInfoEnabled()).thenReturn(false);

        // when
        databaseInstanceGatewayStub.deleteDatabase(
            DatabaseIdGatewayStub.INSTANCE1
        );

        // then
        verify(logger, times(0)).info(anyString());
    }

    @Test
    public void testGetRemoteServerId() throws Exception {
        // given
        String serverId = "868bb6ti";
        DatabaseInstanceGatewayStub databaseInstanceGatewayStub = new DatabaseInstanceGatewayStub();

        // when
        String actual = databaseInstanceGatewayStub.getRemoteServerId();

        // then
        assertThat(actual).isEqualTo(serverId);
    }

}
