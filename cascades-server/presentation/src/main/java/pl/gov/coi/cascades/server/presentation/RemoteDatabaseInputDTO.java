package pl.gov.coi.cascades.server.presentation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Remote databse input data transfer object.
 * Contains information about a remote database input.
 */
@RequiredArgsConstructor
public class RemoteDatabaseInputDTO {

    @Getter
    private final String templateId;
    @Getter
    private final String typeClassName;
    @Getter
    private final String instanceName;

}
