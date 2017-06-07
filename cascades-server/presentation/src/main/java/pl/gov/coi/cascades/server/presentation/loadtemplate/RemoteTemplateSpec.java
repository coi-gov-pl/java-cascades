package pl.gov.coi.cascades.server.presentation.loadtemplate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.gov.coi.cascades.contract.domain.TemplateIdStatus;

import java.io.Serializable;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 12.05.17.
 *
 * Remote template specification describes a remote template connection parameters.
 */
@RequiredArgsConstructor
public class RemoteTemplateSpec implements Serializable {

    private static final long serialVersionUID = 42L;
    @Getter
    private final String id;
    @Getter
    private final String status;
    @Getter
    private final boolean isDefault;
    @Getter
    private final String serverId;
    @Getter
    private final String version;

}
