package pl.gov.coi.cascades.server.domain.loadtemplate;

import lombok.Builder;
import lombok.Getter;
import pl.wavesoftware.eid.utils.EidPreconditions;

import java.io.InputStream;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.05.17.
 * <p>
 * Class for providing request information for loading template.
 */
@Builder
public class Request {

    @Getter
    private final InputStream zipFile;
    @Getter
    private final String name;
    @Getter
    private final Long size;
    @Getter
    private final boolean isEmpty;

    /**
     * Default argument constructor.
     *
     * @param zipFile Given zip file.
     * @param name    Given name of template.
     * @param size    Given size of template.
     * @param isEmpty Given information if template is empty.
     */
    public Request(InputStream zipFile,
                   String name,
                   Long size,
                   boolean isEmpty) {
        this.zipFile = EidPreconditions.checkNotNull(zipFile, "20170524:094331");
        this.name = EidPreconditions.checkNotNull(name, "20170524:094344");
        this.size = EidPreconditions.checkNotNull(size, "20170524:094422");
        this.isEmpty = EidPreconditions.checkNotNull(isEmpty, "20170524:094441");
    }

}
