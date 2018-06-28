package pl.gov.coi.cascades.server.persistance.hibernate;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gov.coi.cascades.server.domain.DatabaseLimitGateway;
import pl.gov.coi.cascades.server.domain.User;

import javax.transaction.Transactional;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
@Transactional
public class DatabaseLimitGatewayImpl implements DatabaseLimitGateway {

    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(DatabaseLimitGatewayImpl.class);
    private Logger logger;

    public DatabaseLimitGatewayImpl() {
        this(
            DEFAULT_LOGGER
        );
    }

    @VisibleForTesting
    DatabaseLimitGatewayImpl(Logger logger) {
        this.logger = logger;
    }

    @Override
    public boolean isPersonalLimitExceeded(User user) {
        // TODO: write an implementation
        return false;
    }

    @Override
    public int getPersonalLimitPerUser(User user) {
        // TODO: write an implementation
        return 100;
    }

    @Override
    public boolean isGlobalLimitExceeded() {
        // TODO: write an implementation
        return false;
    }

    @Override
    public int getGlobalLimit() {
        // TODO: write an implementation
        return 100;
    }
}
