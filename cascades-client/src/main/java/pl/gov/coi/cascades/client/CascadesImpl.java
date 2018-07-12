package pl.gov.coi.cascades.client;

import lombok.Getter;
import pl.gov.coi.cascades.client.presentation.CascadesOperationsLogger;
import pl.gov.coi.cascades.client.service.ServiceExecutionContext;
import pl.gov.coi.cascades.contract.Cascades;
import pl.gov.coi.cascades.contract.configuration.Configuration;
import pl.gov.coi.cascades.contract.configuration.Driver;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.service.CascadesDeleteService;
import pl.gov.coi.cascades.contract.service.CascadesLaunchService;
import pl.gov.coi.cascades.contract.service.RemoteDatabaseRequest;
import pl.gov.coi.cascades.contract.service.RemoteDatabaseSpec;
import pl.gov.coi.cascades.contract.service.WithViolations;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static pl.wavesoftware.eid.utils.EidPreconditions.checkNotNull;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 29.03.17
 */
class CascadesImpl implements Cascades {
    private final CascadesLaunchService cascadesLaunchService;
    private final CascadesDeleteService cascadesDeleteService;
    private final Configuration configuration;
    private final CascadesOperationsLogger operationsLogger;
    private RemoteDatabaseSpec spec;
    @Getter
    private boolean created;

    CascadesImpl(Configuration configuration,
                 CascadesLaunchService cascadesLaunchService,
                 CascadesDeleteService cascadesDeleteService,
                 CascadesOperationsLogger operationsLogger) {
        this.configuration = configuration;
        this.cascadesLaunchService = cascadesLaunchService;
        this.cascadesDeleteService = cascadesDeleteService;
        this.operationsLogger = operationsLogger;
        this.created = false;
    }

    @Override
    public void createDatabase() {
        RemoteDatabaseRequest request = createRequest();
        Future<WithViolations<RemoteDatabaseSpec>> future =
            cascadesLaunchService.launchDatabase(request);
        waitForLaunch(request, future);
    }

    @Override
    public void removeDatabase() {
        if (isCreated()) {
            DatabaseId databaseId = getSpec().getDatabaseId();
            Future<WithViolations<Void>> future =
                cascadesDeleteService.deleteDatabase(databaseId);
            waitForRemoval(databaseId, future);
        }
    }

    @Override
    public RemoteDatabaseSpec getSpec() {
        return spec;
    }

    private RemoteDatabaseRequest createRequest() {
        Driver driver = configuration.getDriver();
        return new RemoteDatabaseRequest(
            driver.getTemplateId().orNull(),
            configuration.getInstanceName().orNull()
        );
    }

    private void waitForLaunch(RemoteDatabaseRequest request,
                               Future<WithViolations<RemoteDatabaseSpec>> future) {
        waitFor(
            context("launch new database instance", request),
            future,
            new Consumer<RemoteDatabaseSpec>() {
                @Override
                public void consume(RemoteDatabaseSpec inputSpec) {
                    spec = checkNotNull(inputSpec, "20170330:101619");
                    created = true;
                }
            }
        );
    }

    private void waitForRemoval(DatabaseId databaseId,
                                Future<WithViolations<Void>> future) {
        waitFor(
            context("delete of running database instance", databaseId),
            future,
            new Consumer<Void>() {
                @Override
                public void consume(Void input) {
                    created = false;
                }
            }
        );
    }

    private <T> void waitFor(ServiceExecutionContext executionContext,
                             Future<WithViolations<T>> future,
                             Consumer<T> consumer) {
        long max = configuration.getTimeoutInSeconds();
        for (long i = 0; i < max; i++) {
            try {
                waitForOneSecond(executionContext, future, consumer);
            } catch (InterruptedException e) {
                max++;
                operationsLogger.sinkInterruptedException(e);
                Thread.currentThread().interrupt();
            } catch (ExecutionException ex) {
                operationsLogger.logDatabaseCreateFailed(ex);
            } catch (TimeoutException ex) {
                operationsLogger.sinkTimeoutException(ex);
            }
        }
    }

    private <T> void waitForOneSecond(ServiceExecutionContext executionContext,
                                      Future<WithViolations<T>> future,
                                      Consumer<T> consumer)
        throws InterruptedException, ExecutionException, TimeoutException {

        WithViolations<T> withViolations = future.get(1, TimeUnit.SECONDS);
        if (withViolations.getViolations().iterator().hasNext()) {
            operationsLogger.logDatabaseCreateFailed(
                executionContext,
                withViolations.getViolations()
            );
        } else {
            consumer.consume(
                checkNotNull(withViolations.getTarget(), "20170330:154214")
            );
        }
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

    private interface Consumer<T> {
        void consume(T input);
    }

}
