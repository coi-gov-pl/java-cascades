package pl.gov.coi.cascades.contract.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@RequiredArgsConstructor
@ToString
@Builder
public class Template implements Serializable {

    private static final long serialVersionUID = 42L;

    @Getter
    private final String id;

    @Getter
    private final TemplateIdStatus status;

    @Getter
    private final boolean isDefault;

    @Getter
    private final String serverId;

    @Getter
    private final String version;

}
