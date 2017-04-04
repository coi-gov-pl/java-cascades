package pl.gov.coi.cascades.supplier.uri;

import com.google.common.base.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.wavesoftware.eid.utils.EidPreconditions;

import javax.annotation.Nonnull;
import java.net.URI;
import java.net.URISyntaxException;

import static pl.wavesoftware.eid.utils.EidPreconditions.tryToExecute;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 30.03.17
 */
@RequiredArgsConstructor
public class SchemeHostPortUri implements Supplier<URI> {

    private static final int HTTP_PORT = 80;
    private static final int HTTPS_PORT = 443;

    private final String scheme;
    private final String host;
    private final int port;
    @Setter
    private String path;

    @Override
    public URI get() {
        final int properPort;
        if ((port == HTTP_PORT && "http".equals(scheme))
            || (port == HTTPS_PORT && "https".equals(scheme))) {
            properPort = -1;
        } else {
            properPort = port;
        }
        return tryToExecute(new EidPreconditions.UnsafeSupplier<URI>() {

            @Override @Nonnull
            public URI get() throws URISyntaxException {
                return new URI(
                    scheme,
                    null,
                    host,
                    properPort,
                    path,
                    null,
                    null
                );
            }
        }, "20170330:111419");
    }
}
