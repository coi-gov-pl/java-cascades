package pl.gov.coi.cascades.server.domain.loadtemplate;

import lombok.Builder;
import lombok.Getter;
import pl.wavesoftware.eid.utils.EidPreconditions;

import javax.annotation.WillNotClose;
import java.io.InputStream;

import static pl.wavesoftware.eid.utils.EidPreconditions.checkNotNull;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.05.17.
 * <p>
 * Class for providing request information for loading template.
 */
@Builder
public class Request {

    @Getter
    private final String name;
    @Getter
    private final boolean isEmpty;
    @Getter
    private final Upload upload;

    /**
     * Default argument constructor.
     *
     * @param upload  Given upload.
     * @param name    Given name of template.
     * @param isEmpty Given information if template is empty.
     */
    public Request(String name,
                   boolean isEmpty,
                   Upload upload) {

        this.name = checkNotNull(name, "20170524:094344");
        this.isEmpty = checkNotNull(isEmpty, "20170524:094441");
        this.upload = upload;
    }

}
