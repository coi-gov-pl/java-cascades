package pl.gov.coi.cascades.client.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.http.options.Option;
import com.mashape.unirest.http.options.Options;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.apache.http.HttpStatus;
import pl.gov.coi.cascades.contract.service.CascadesLaunchService;
import pl.gov.coi.cascades.contract.service.RemoteDatabaseRequest;
import pl.gov.coi.cascades.contract.service.RemoteDatabaseSpec;
import pl.gov.coi.cascades.contract.service.Violation;
import pl.gov.coi.cascades.contract.service.WithViolations;
import pl.gov.coi.cascades.supplier.string.InputStreamString;
import pl.wavesoftware.eid.exceptions.Eid;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;
import pl.wavesoftware.eid.utils.EidPreconditions.UnsafeSupplier;

import javax.annotation.Nonnull;
import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static pl.wavesoftware.eid.utils.EidPreconditions.tryToExecute;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 30.03.17
 */
class RestCascadesLaunchService implements CascadesLaunchService {
    private final URI cascadesServer;
    private final ObjectMapper objectMapper;

    RestCascadesLaunchService(URI cascadesServer, ObjectMapper objectMapper) {
        this.cascadesServer = cascadesServer;
        this.objectMapper = objectMapper;
    }

    @Override
    public Future<WithViolations<RemoteDatabaseSpec>> launchDatabase(
        final RemoteDatabaseRequest request) {
        return new FutureTask<>(new Callable<WithViolations<RemoteDatabaseSpec>>() {
            @Override
            public RemoteDatabaseSpecWithViolations call() throws Exception {
                return launchDatabaseViaRest(request);
            }
        });
    }

    private RemoteDatabaseSpecWithViolations launchDatabaseViaRest(
        final RemoteDatabaseRequest request) {

        Options.setOption(Option.OBJECT_MAPPER, objectMapper);
        this.getClass();
        final RequestBodyEntity unirest = Unirest
            .post(cascadesServer.toString() + "/databases")
            .header("accept", "application/json")
            .body(request);

        HttpResponse<RemoteDatabaseSpecWithViolations> response =
            tryToExecute(new UnsafeSupplier<HttpResponse<RemoteDatabaseSpecWithViolations>>() {
                @Override @Nonnull
                public HttpResponse<RemoteDatabaseSpecWithViolations> get()
                    throws UnirestException {
                    return unirest.asObject(RemoteDatabaseSpecWithViolations.class);
                }
            }, "20170330:130713");

        if (expectedAnswer(response)) {
            return response.getBody();
        } else {
            throw new EidIllegalStateException(
                new Eid("20170330:130948"),
                "Remote server returned error (%d) and body was - \n\n%s",
                response.getStatusText(),
                response.getStatus(),
                getBodyTextOutOf(response)
            );
        }
    }

    private static String getBodyTextOutOf(
        HttpResponse<RemoteDatabaseSpecWithViolations> response) {
        final InputStream raw = response.getRawBody();
        return new InputStreamString(raw).get();
    }

    private static boolean expectedAnswer(
        HttpResponse<RemoteDatabaseSpecWithViolations> response) {

        return response.getStatus() == HttpStatus.SC_OK
            || response.getStatus() == HttpStatus.SC_BAD_REQUEST;
    }

    private static final class RemoteDatabaseSpecWithViolations
        extends WithViolations<RemoteDatabaseSpec> {

        public RemoteDatabaseSpecWithViolations(RemoteDatabaseSpec target) {
            super(target);
        }

        public RemoteDatabaseSpecWithViolations(Iterable<Violation> errors) {
            super(errors);
        }
    }

}
