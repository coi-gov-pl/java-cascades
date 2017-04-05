package pl.gov.coi.cascades.client.presentation;

import pl.gov.coi.cascades.client.service.ServiceExecutionContext;
import pl.gov.coi.cascades.contract.service.RemoteDatabaseSpec;
import pl.gov.coi.cascades.contract.service.Violation;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 30.03.17
 */
public interface CascadesOperationsLogger {
    /**
     * Logs a database created event
     *
     * @param spec a database spec
     */
    void logDatabaseCreated(RemoteDatabaseSpec spec);

    /**
     * Logs an event in with database failed to create
     *
     * @param ex an exception
     */
    void logDatabaseCreateFailed(ExecutionException ex);

    /**
     * Logs a database removed event
     *
     * @param spec a database spec
     */
    void logDatabaseRemoved(RemoteDatabaseSpec spec);

    /**
     * Will deeply hide a Timeout Exception. It should be used to prevent irrelevant
     * log information to leak into log files.
     *
     * @param ex a Timeout exception
     */
    void sinkTimeoutException(TimeoutException ex);

    /**
     * Will deeply hide an Interrupted Exception. It should be used to prevent irrelevant
     * log information to leak into log files.
     *
     * @param ex an Interrupted exception
     */
    void sinkInterruptedException(InterruptedException ex);

    /**
     * Log situation that request failed because of business logic violations
     *
     * @param executionContext a execution context that failed
     * @param violations       a list of violations
     */
    void logDatabaseCreateFailed(ServiceExecutionContext<?> executionContext,
                                 Iterable<Violation> violations);

}
