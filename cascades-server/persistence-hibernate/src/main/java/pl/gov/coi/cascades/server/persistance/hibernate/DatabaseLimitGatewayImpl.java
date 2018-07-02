package pl.gov.coi.cascades.server.persistance.hibernate;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gov.coi.cascades.server.domain.DatabaseLimitGateway;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.persistance.hibernate.mapper.DatabaseInstanceMapper;

import javax.transaction.Transactional;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
@Transactional
public class DatabaseLimitGatewayImpl implements DatabaseLimitGateway {

    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(DatabaseLimitGatewayImpl.class);
    private static final int USER_LIMIT = 100;
    private static final boolean PERSONAL_LIMIT = false;
    private static final boolean GLOBAL_LIMIT = false;

    private Logger logger;
    private final DatabaseInstanceMapper databaseInstanceMapper;

    public DatabaseLimitGatewayImpl(DatabaseInstanceMapper databaseInstanceMapper) {
        this(
            databaseInstanceMapper,
            DEFAULT_LOGGER
        );
    }

    @VisibleForTesting
    DatabaseLimitGatewayImpl(DatabaseInstanceMapper databaseInstanceMapper,
                             Logger logger) {
        this.databaseInstanceMapper = databaseInstanceMapper;
        this.logger = logger;
    }

    @Override
    public boolean isPersonalLimitExceeded(User user) {
        // TODO: write an implementation
        return PERSONAL_LIMIT;
    }

    @Override
    public int getPersonalLimitPerUser(User user) {
        // TODO: write an implementation
        return USER_LIMIT;
    }

    @Override
    public boolean isGlobalLimitExceeded() {
        // TODO: write an implementation
        return GLOBAL_LIMIT;
    }

    @Override
    @Deprecated
    public int getGlobalLimit() {
        // TODO: write an implementation
        throw new UnsupportedOperationException("Not yet implemented!");
    }
}
