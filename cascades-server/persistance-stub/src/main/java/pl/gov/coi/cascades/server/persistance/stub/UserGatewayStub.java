package pl.gov.coi.cascades.server.persistance.stub;

import java.util.Optional;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.UserGateway;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.02.17.
 */
final class UserGatewayStub implements UserGateway {

    @Override
    public Optional<User> find(String user) {
        return null;
    }

    @Override
    public void save(User user) {

    }
}
