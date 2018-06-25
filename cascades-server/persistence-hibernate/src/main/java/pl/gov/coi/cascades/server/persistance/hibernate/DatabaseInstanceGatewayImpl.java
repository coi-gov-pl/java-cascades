package pl.gov.coi.cascades.server.persistance.hibernate;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseInstanceGateway;
import pl.gov.coi.cascades.server.domain.DatabaseTypeClassNameService;
import pl.gov.coi.cascades.server.persistance.hibernate.mapper.DatabaseInstanceMapper;

import javax.inject.Inject;

/**
 * @author Łukasz Małek <lukasz.malek@coi.gov.pl>
 */
public class DatabaseInstanceGatewayImpl implements DatabaseInstanceGateway {

    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(DatabaseInstanceGatewayImpl.class);
    private Logger logger;
    private final DatabaseInstanceMapper databaseInstanceMapper;

    @Inject
    public DatabaseInstanceGatewayImpl(DatabaseTypeClassNameService databaseTypeClassNameService) {
        this(
            new DatabaseInstanceMapper(
                databaseTypeClassNameService
            ),
            DEFAULT_LOGGER
        );
    }

    @VisibleForTesting
    DatabaseInstanceGatewayImpl(DatabaseInstanceMapper databaseInstanceMapper,
                                Logger logger) {
        this.databaseInstanceMapper = databaseInstanceMapper;
        this.logger = logger;
    }

    @Override
    public DatabaseInstance launchDatabase(DatabaseInstance databaseInstance) {
        return null;
    }

    @Override
    public void deleteDatabase(DatabaseInstance databaseInstance) {

    }

    @Override
    public String getRemoteServerId() {
        return null;
    }
}
