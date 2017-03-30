package pl.gov.coi.cascades.server.persistance.hibernate.mapper;

import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseTypeClassNameService;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;
import pl.wavesoftware.eid.utils.EidPreconditions;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.03.17.
 */
public class UserMapper implements Mapper<User, pl.gov.coi.cascades.server.domain.User> {

    private final DatabaseTypeClassNameService databaseTypeClassNameService;
    private final DatabaseInstanceMapper databaseInstanceMapper;

    @Inject
    public UserMapper(DatabaseTypeClassNameService databaseTypeClassNameService) {
        this.databaseTypeClassNameService = databaseTypeClassNameService;
        databaseInstanceMapper = new DatabaseInstanceMapper(databaseTypeClassNameService);
    }

    @Override
    public User toHibernateEntity(pl.gov.coi.cascades.server.domain.User user) {
        EidPreconditions.checkNotNull(user.getUsername(), "20170327:102134");
        EidPreconditions.checkNotNull(user.getId(), "20170327:102136");
        EidPreconditions.checkNotNull(user.getEmail(), "20170327:102140");

        User hibernateUser = new User();
        hibernateUser.setUsername(user.getUsername());
        hibernateUser.setId(Long.parseLong(user.getId()));
        hibernateUser.setEmail(user.getEmail());
        for (DatabaseInstance databaseInstance : user.getDatabases()) {
            hibernateUser.getDatabases().add(databaseInstanceMapper.toHibernateEntity(databaseInstance));
        }

        return hibernateUser;
    }

    @Override
    public pl.gov.coi.cascades.server.domain.User fromHibernateEntity(User user) {
        EidPreconditions.checkNotNull(user.getUsername(), "20170327:085540");
        EidPreconditions.checkNotNull(user.getId(), "20170327:085557");
        EidPreconditions.checkNotNull(user.getEmail(), "20170327:085606");

        Collection<DatabaseInstance> databases = new HashSet<>();
        for (pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance databaseInstance : user.getDatabases()) {
            databases.add(databaseInstanceMapper.fromHibernateEntity(databaseInstance));
        }

        return new pl.gov.coi.cascades.server.domain.User(
            user.getUsername(),
            Long.toString(user.getId()),
            user.getEmail(),
            databases
        );
    }

}
