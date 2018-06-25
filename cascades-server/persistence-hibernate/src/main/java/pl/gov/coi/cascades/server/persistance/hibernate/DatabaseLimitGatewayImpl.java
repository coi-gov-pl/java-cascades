package pl.gov.coi.cascades.server.persistance.hibernate;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gov.coi.cascades.server.domain.DatabaseLimitGateway;
import pl.gov.coi.cascades.server.domain.DatabaseTypeClassNameService;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.persistance.hibernate.mapper.DatabaseInstanceMapper;

import javax.inject.Inject;

/**
 * @author Łukasz Małek <lukasz.malek@coi.gov.pl>
 */
public class DatabaseLimitGatewayImpl implements DatabaseLimitGateway {

    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(DatabaseLimitGatewayImpl.class);
    private Logger logger;
    private final DatabaseInstanceMapper databaseInstanceMapper;

    @Inject
    public DatabaseLimitGatewayImpl(DatabaseTypeClassNameService databaseTypeClassNameService) {
        this(
            new DatabaseInstanceMapper(
                databaseTypeClassNameService
            ),
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
        return false;
    }

    @Override
    public int getPersonalLimitPerUser(User user) {
        return 0;
    }

    @Override
    public boolean isGlobalLimitExceeded() {
        return false;
    }

    @Override
    public int getGlobalLimit() {
        return 0;
    }
}
