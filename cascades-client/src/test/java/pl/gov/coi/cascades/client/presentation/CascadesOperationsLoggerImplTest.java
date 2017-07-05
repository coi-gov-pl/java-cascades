package pl.gov.coi.cascades.client.presentation;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.slf4j.Logger;
import pl.gov.coi.cascades.client.service.ServiceExecutionContext;
import pl.gov.coi.cascades.contract.service.RemoteDatabaseSpec;
import pl.gov.coi.cascades.contract.service.Violation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 04.07.17
 */
public class CascadesOperationsLoggerImplTest {

    @Mock
    private Logger logger;

    @Mock
    private ServiceExecutionContext request;

    @Mock
    private RemoteDatabaseSpec remoteDatabaseSpec;

    @Mock
    private TimeoutException timeoutException;

    @Mock
    private InterruptedException interruptedException;

    @Mock
    private ExecutionException executionException;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testLogDatabaseCreated() throws Exception {
        // given
        when(logger.isInfoEnabled()).thenReturn(true);
        CascadesOperationsLoggerImpl cascadesOperationsLogger = new CascadesOperationsLoggerImpl(
            logger
        );

        // when
        cascadesOperationsLogger.logDatabaseCreated(
            remoteDatabaseSpec
        );

        // then
        verify(logger).info(contains("20170704:144802"));
        verify(logger).info(contains("Database: " +
            remoteDatabaseSpec.toString() +
            " has been created successfully."));
    }

    @Test
    public void testLogDatabaseCreateFailed() throws Exception {
        // given
        when(logger.isErrorEnabled()).thenReturn(true);
        CascadesOperationsLoggerImpl cascadesOperationsLogger = new CascadesOperationsLoggerImpl(
            logger
        );

        // when
        cascadesOperationsLogger.logDatabaseCreateFailed(
            executionException
        );

        // then
        verify(logger).error(contains("20170704:145034"));
        verify(logger).error(contains("Database has not been created because of: " +
            executionException));
    }

    @Test
    public void testLogDatabaseRemoved() throws Exception {
        // given
        when(logger.isInfoEnabled()).thenReturn(true);
        CascadesOperationsLoggerImpl cascadesOperationsLogger = new CascadesOperationsLoggerImpl(
            logger
        );

        // when
        cascadesOperationsLogger.logDatabaseRemoved(
            remoteDatabaseSpec
        );

        // then
        verify(logger).info(contains("20170704:145551"));
        verify(logger).info(contains("Database: " +
            remoteDatabaseSpec.toString() +
            " has been deleted successfully."));
    }

    @Test
    public void testSinkTimeoutException() throws Exception {
        // given
        when(logger.isErrorEnabled()).thenReturn(true);
        when(timeoutException.getMessage()).thenReturn("Timeout exception occurred.");
        when(timeoutException.getLocalizedMessage()).thenReturn("operation");
        CascadesOperationsLoggerImpl cascadesOperationsLogger = new CascadesOperationsLoggerImpl(
            logger
        );

        // when
        cascadesOperationsLogger.sinkTimeoutException(
            timeoutException
        );

        // then
        verify(logger).error(contains("20170705:092615"));
        verify(logger).error(contains("There was an error: " +
            timeoutException.getMessage() +
            " localized in: " +
            timeoutException.getLocalizedMessage()));
    }

    @Test
    public void testSinkInterruptedException() throws Exception {
        // given
        when(logger.isErrorEnabled()).thenReturn(true);
        when(interruptedException.getMessage()).thenReturn("Interrupted exception occurred.");
        when(interruptedException.getLocalizedMessage()).thenReturn("operation");
        CascadesOperationsLoggerImpl cascadesOperationsLogger = new CascadesOperationsLoggerImpl(
            logger
        );

        // when
        cascadesOperationsLogger.sinkInterruptedException(
            interruptedException
        );

        // then
        verify(logger).error(contains("20170705:092910"));
        verify(logger).error(contains("There was an error: " +
            interruptedException.getMessage() +
            " localized in: " +
            interruptedException.getLocalizedMessage()));
    }

    @Test
    public void testLogDatabaseCreateFailedWithViolations() throws Exception {
        // given
        Collection<Violation> violations = new ArrayList<>();
        ServiceExecutionContext context = context(
            "operation",
            request
        );
        violations.add(new Violation() {
            @Override
            public String getMessage() {
                return "An error occurred.";
            }

            @Override
            public String getPropertyPath() {
                return "error";
            }
        });
        when(logger.isErrorEnabled()).thenReturn(true);
        CascadesOperationsLoggerImpl cascadesOperationsLogger = new CascadesOperationsLoggerImpl(
            logger
        );

        // when
        cascadesOperationsLogger.logDatabaseCreateFailed(
            context,
            violations
        );

        // then
        verify(logger).error(contains("20170704:145935"));
        verify(logger).error(contains("Request: " +
            context.getRequest() +
            " with operation: " +
            context.getOperationName() +
            " failed because of business logic violations: " +
            violations.toString()));
    }

    private ServiceExecutionContext context(final String operationName, final Object request) {
        return new ServiceExecutionContext() {
            @Override
            public String getOperationName() {
                return operationName;
            }

            @Override
            public Object getRequest() {
                return request;
            }
        };
    }

}
