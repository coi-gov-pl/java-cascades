package pl.gov.coi.cascades.server.domain.deletedatabase;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.server.domain.User;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 09.03.17.
 */
@Builder
@RequiredArgsConstructor
public class DeleteLaunchedDatabaseInstanceRequest {

    @Getter
    private final DatabaseId databaseId;
    @Getter
    private final User user;

}
