package pl.gov.coi.cascades.server.presentation.stub;

import org.springframework.stereotype.Component;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.presentation.UserSession;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 03.03.17.
 */
@Component
final class UserSessionStub implements UserSession {
    private User user = new User("jrambo", "fcweccf", "jrambo@example.org");

    @Override
    public User getSignedInUser() {
        return user;
    }
}
