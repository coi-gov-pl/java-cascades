package pl.gov.coi.cascades.server.persistance.stub;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.slf4j.Logger;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
public class DatabaseUserGatewayStubTest {

    @Mock
    private DatabaseInstance databaseInstance;

    @Mock
    private Logger logger;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void shouldCreateDefaultConstructor() {
        // when
        DatabaseUserGatewayStub userGatewayStub = new DatabaseUserGatewayStub();

        // then
        assertNotNull(userGatewayStub);
    }

    @Test
    public void shouldDeleteUserWhenLoggerIsNotInfoEnabled() throws Exception {
        // given
        when(logger.isInfoEnabled()).thenReturn(false);
        DatabaseUserGatewayStub userGatewayStub = new DatabaseUserGatewayStub(
            logger
        );

        // when
        userGatewayStub.deleteUser(
            databaseInstance
        );

        // then
        verify(logger, times(0)).info(anyString());
    }

    @Test
    public void shouldCreateUserWhenLoggerIsNotInfoEnabled() throws Exception {
        // given
        when(logger.isInfoEnabled()).thenReturn(false);
        DatabaseUserGatewayStub userGatewayStub = new DatabaseUserGatewayStub(
            logger
        );

        // when
        userGatewayStub.createUser(
            databaseInstance
        );

        // then
        verify(logger, times(0)).info(anyString());
    }

    @Test
    public void testCreateTemplate() throws Exception {
        // given
        when(logger.isInfoEnabled()).thenReturn(true);
        DatabaseUserGatewayStub userGatewayStub = new DatabaseUserGatewayStub(
            logger
        );

        // when
        userGatewayStub.createUser(
            databaseInstance
        );

        // then
        verify(logger).info(contains("20180704:094608"));
        verify(logger).info(contains("Given user has been successfully created."));
    }

    @Test
    public void testDeleteTemplate() throws Exception {
        // given
        when(logger.isInfoEnabled()).thenReturn(true);
        DatabaseUserGatewayStub userGatewayStub = new DatabaseUserGatewayStub(
            logger
        );

        // when
        userGatewayStub.deleteUser(
            databaseInstance
        );

        // then
        verify(logger).info(contains("20180704:094708"));
        verify(logger).info(contains("Given user has been successfully deleted."));
    }
}
