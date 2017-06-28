package pl.gov.coi.cascades.server.domain.loadtemplate;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 28.06.17
 */
@RequiredArgsConstructor
@ToString
@Builder
class TemplateMetadata {

    @Getter
    private final String id;

    @Getter
    private final boolean isDefault;

    @Getter
    private final String serverId;

    @Getter
    private final String version;

}
