package pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.user;

import pl.gov.coi.cascades.server.persistance.hibernate.development.DevelopmentBean;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import java.util.function.Supplier;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.03.17.
 */
@DevelopmentBean
public class JohnRamboSupplier implements Supplier<User> {

    @Override
    public User get() {
        User user = new User();
        user.setUsername("jrambo");
        user.setEmail("jrambo@example.org");
        return user;
    }

}
