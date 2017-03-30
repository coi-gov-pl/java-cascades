package pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.user;


import pl.gov.coi.cascades.server.persistance.hibernate.development.DevelopmentBean;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import java.security.SecureRandom;
import java.util.function.Supplier;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.03.17.
 */
@DevelopmentBean
public class MikolajRoznerskiSupplier implements Supplier<User> {

    @Override
    public User get() {
        User user = new User();
        user.setUsername("Mikołaj Roznerski" + new SecureRandom().nextInt());
        user.setEmail("mikolaj.rozneski@example.com");
        return user;
    }

}
