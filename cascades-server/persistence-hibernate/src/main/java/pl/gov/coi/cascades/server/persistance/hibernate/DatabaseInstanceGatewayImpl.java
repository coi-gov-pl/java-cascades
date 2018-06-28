package pl.gov.coi.cascades.server.persistance.hibernate;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseInstanceGateway;

import javax.transaction.Transactional;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
@Transactional
public class DatabaseInstanceGatewayImpl implements DatabaseInstanceGateway {

    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(DatabaseInstanceGatewayImpl.class);
    private Logger logger;

    public DatabaseInstanceGatewayImpl() {
        this(
            DEFAULT_LOGGER
        );
    }

    @VisibleForTesting
    DatabaseInstanceGatewayImpl(Logger logger) {
        this.logger = logger;
    }

    @Override
    @Deprecated
    public DatabaseInstance launchDatabase(DatabaseInstance databaseInstance) {
        // TODO: write an implementation
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @Override
    @Deprecated
    public void deleteDatabase(DatabaseInstance databaseInstance) {
        // TODO: write an implementation
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @Override
    @Deprecated
    public String getRemoteServerId() {
        // TODO: write an implementation
        throw new UnsupportedOperationException("Not yet implemented!");
    }
}
