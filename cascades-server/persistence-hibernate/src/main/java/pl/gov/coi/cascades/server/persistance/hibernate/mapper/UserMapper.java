package pl.gov.coi.cascades.server.persistance.hibernate.mapper;

import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;
import pl.wavesoftware.eid.utils.EidPreconditions;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.03.17.
 */
public class UserMapper implements Mapper<User, pl.gov.coi.cascades.server.domain.User> {

    @Override
    public User toHibernateEntity(pl.gov.coi.cascades.server.domain.User user) {
        EidPreconditions.checkNotNull(user.getUsername(), "20170327:102134");
        EidPreconditions.checkNotNull(user.getId(), "20170327:102136");
        EidPreconditions.checkNotNull(user.getEmail(), "20170327:102140");

        User hibernateUser = new User();
        hibernateUser.setUsername(user.getUsername());
        hibernateUser.setId(Long.parseLong(user.getId()));
        hibernateUser.setEmail(user.getEmail());

        return hibernateUser;
    }

    @Override
    public pl.gov.coi.cascades.server.domain.User fromHibernateEntity(User user) {
        EidPreconditions.checkNotNull(user.getUsername(), "20170327:085540");
        EidPreconditions.checkNotNull(user.getId(), "20170327:085557");
        EidPreconditions.checkNotNull(user.getEmail(), "20170327:085606");

        return new pl.gov.coi.cascades.server.domain.User(
            user.getUsername(),
            Long.toString(user.getId()),
            user.getEmail()
        );
    }

}
