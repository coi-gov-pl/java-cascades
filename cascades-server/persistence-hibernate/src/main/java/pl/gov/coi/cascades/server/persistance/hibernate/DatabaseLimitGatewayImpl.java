package pl.gov.coi.cascades.server.persistance.hibernate;

import pl.gov.coi.cascades.server.domain.DatabaseLimitGateway;
import pl.gov.coi.cascades.server.domain.User;

import javax.transaction.Transactional;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
@Transactional
public class DatabaseLimitGatewayImpl implements DatabaseLimitGateway {

    @Override
    @Deprecated
    public boolean isPersonalLimitExceeded(User user) {
        // TODO: write an implementation
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @Override
    @Deprecated
    public int getPersonalLimitPerUser(User user) {
        // TODO: write an implementation
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @Override
    @Deprecated
    public boolean isGlobalLimitExceeded() {
        // TODO: write an implementation
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @Override
    @Deprecated
    public int getGlobalLimit() {
        // TODO: write an implementation
        throw new UnsupportedOperationException("Not yet implemented!");
    }
}
