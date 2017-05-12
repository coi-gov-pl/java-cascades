package pl.gov.coi.cascades.server.domain.deletedatabase;

import lombok.Builder;
import lombok.Getter;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.server.domain.User;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 09.03.17.
 */
@Builder
public class Request {

    @Getter
    private final DatabaseId databaseId;
    @Getter
    private final User user;

    public Request(DatabaseId databaseId, User user) {
        this.databaseId = databaseId;
        this.user = user;
    }
}
