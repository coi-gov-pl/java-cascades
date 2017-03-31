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
class CascadesOperationsLoggerImpl implements CascadesOperationsLogger {
    @Override
    public void logDatabaseCreated(RemoteDatabaseSpec spec) {
        // TODO: write some implementation here ;-P
    }

    @Override
    public void logDatabaseCreateFailed(ExecutionException ex) {
        // TODO: write some implementation here ;-P
    }

    @Override
    public void logDatabaseRemoved(RemoteDatabaseSpec spec) {
        // TODO: write some implementation here ;-P
    }

    @Override
    public void sinkTimeoutException(TimeoutException ex) {
        // TODO: write some implementation here ;-P
    }

    @Override
    public void sinkInterruptedException(InterruptedException ex) {
        // TODO: write some implementation here ;-P
    }

    @Override
    public void logDatabaseCreateFailed(ServiceExecutionContext<?> executionContext,
                                        Iterable<Violation> violations) {
        // TODO: write some implementation here ;-P
    }
}
