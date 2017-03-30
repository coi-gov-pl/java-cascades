package pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.user;

import pl.gov.coi.cascades.server.persistance.hibernate.development.DevelopmentBean;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import java.util.function.Supplier;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 29.03.17.
 */
@DevelopmentBean
public class MichaelSupplier implements Supplier<User> {

    @Override
    public User get() {
        User user = new User();
        user.setUsername("michael");
        user.setEmail("michael@example.org");
        return user;
    }

}
