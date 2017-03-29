package pl.gov.coi.cascades.server.persistance.hibernate.development;

import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import java.util.function.Supplier;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 29.03.17.
 */
@DevelopmentBean
class JackieSupplier implements Supplier<User> {
    @Override
    public User get() {
        User user = new User();
        user.setUsername("jackie");
        user.setEmail("jackie@example.org");
        return user;
    }
}
