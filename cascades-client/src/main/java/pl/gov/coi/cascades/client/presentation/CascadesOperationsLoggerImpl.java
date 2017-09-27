package pl.gov.coi.cascades.client.presentation;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gov.coi.cascades.client.service.ServiceExecutionContext;
import pl.gov.coi.cascades.contract.service.RemoteDatabaseSpec;
import pl.gov.coi.cascades.contract.service.Violation;
import pl.wavesoftware.eid.exceptions.Eid;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 30.03.17
 */
@AllArgsConstructor
class CascadesOperationsLoggerImpl implements CascadesOperationsLogger {

    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(CascadesOperationsLoggerImpl.class);
    private Logger logger;

    CascadesOperationsLoggerImpl() {
        this(DEFAULT_LOGGER);
    }

    @Override
    public void logDatabaseCreated(RemoteDatabaseSpec spec) {
        if (logger.isInfoEnabled()) {
            logger.info(new Eid("20170704:144802").makeLogMessage(
                "Database: " +
                    spec.toString() +
                    " has been created successfully."
            ));
        }
    }

    @Override
    public void logDatabaseCreateFailed(ExecutionException ex) {
        if (logger.isErrorEnabled()) {
            logger.error(new Eid("20170704:145034").makeLogMessage(
                "Database has not been created because of: " +
                    ex
            ));
        }
    }

    @Override
    public void logDatabaseRemoved(RemoteDatabaseSpec spec) {
        if (logger.isInfoEnabled()) {
            logger.info(new Eid("20170704:145551").makeLogMessage(
                "Database: " +
                    spec.toString() +
                    " has been deleted successfully."
            ));
        }
    }

    @Override
    public void sinkTimeoutException(TimeoutException ex) {
        if (logger.isErrorEnabled()) {
            logger.error(new Eid("20170705:092615").makeLogMessage(
                "There was an error: " +
                    ex.getMessage() +
                    " localized in: " +
                    ex.getLocalizedMessage()
            ));
        }
    }

    @Override
    public void sinkInterruptedException(InterruptedException ex) {
        if (logger.isErrorEnabled()) {
            logger.error(new Eid("20170705:092910").makeLogMessage(
                "There was an error: " +
                    ex.getMessage() +
                    " localized in: " +
                    ex.getLocalizedMessage()
            ));
        }
    }

    @Override
    public void logDatabaseCreateFailed(ServiceExecutionContext<?> executionContext,
                                        Iterable<Violation> violations) {
        if (logger.isErrorEnabled()) {
            logger.error(new Eid("20170704:145935").makeLogMessage(
                "Request: " +
                    executionContext.getRequest() +
                    " with operation: " +
                    executionContext.getOperationName() +
                    " failed because of business logic violations: " +
                    violations.toString()
            ));
        }
    }
}
