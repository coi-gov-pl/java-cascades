package pl.gov.coi.cascades.server.persistance.hibernate.mappers;

import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.03.17.
 */
public class UserMapper implements Mapper<User, pl.gov.coi.cascades.server.domain.User> {

    @Override
    public User toHibernateEntity(pl.gov.coi.cascades.server.domain.User user) {
        return null;
    }

    @Override
    public pl.gov.coi.cascades.server.domain.User fromHibernateEntity(User user) {
        return null;
    }

}
