package pl.gov.coi.cascades.server.persistance.hibernate.mapper;

import com.google.common.annotations.VisibleForTesting;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;
import pl.gov.coi.cascades.server.domain.DatabaseIdMapper;
import pl.gov.coi.cascades.server.domain.DatabaseTypeClassNameService;
import pl.gov.coi.cascades.server.domain.Mapper;
import pl.gov.coi.cascades.server.domain.launchdatabase.UsernameAndPasswordCredentialsImpl;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.Credentials;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseStatus;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.NetworkBind;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.Arrays;

import static pl.wavesoftware.eid.utils.EidPreconditions.checkNotNull;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.03.17.
 */
public class DatabaseInstanceMapper implements Mapper<DatabaseInstance, pl.gov.coi.cascades.server.domain.DatabaseInstance> {

    private static final TemplateIdMapper DEFAULT_TEMPLATE_ID_MAPPER = new TemplateIdMapper();
    private static final DatabaseIdMapper DEFAULT_DATABASE_ID_MAPPER = new DatabaseIdMapper();
    private final DatabaseTypeClassNameService databaseTypeClassNameService;
    private final TemplateIdMapper templateIdMapper;
    private final DatabaseIdMapper databaseIdMapper;

    /**
     * Default constructor.
     *
     * @param databaseTypeClassNameService Service for database type class name.
     */
    public DatabaseInstanceMapper(DatabaseTypeClassNameService databaseTypeClassNameService) {
        this(
            databaseTypeClassNameService,
            DEFAULT_TEMPLATE_ID_MAPPER,
            DEFAULT_DATABASE_ID_MAPPER
        );
    }

    @Inject
    @VisibleForTesting
    DatabaseInstanceMapper(DatabaseTypeClassNameService databaseTypeClassNameService,
                           TemplateIdMapper templateIdMapper,
                           DatabaseIdMapper databaseIdMapper) {
        this.databaseTypeClassNameService = databaseTypeClassNameService;
        this.templateIdMapper = templateIdMapper;
        this.databaseIdMapper = databaseIdMapper;
    }

    @Override
    public DatabaseInstance toHibernateEntity(@Nonnull pl.gov.coi.cascades.server.domain.DatabaseInstance databaseInstance) {
        checkNotNull(databaseInstance.getTemplate(), "20170414:111003");
        checkNotNull(databaseInstance.getInstanceName(), "20170414:111006");
        checkNotNull(databaseInstance.getReuseTimes(), "20170414:111009");
        checkNotNull(databaseInstance.getDatabaseName(), "20170414:111012");
        checkNotNull(databaseInstance.getCredentials(), "20170414:111015");
        checkNotNull(databaseInstance.getNetworkBind(), "20170414:111025");
        checkNotNull(databaseInstance.getStatus(), "20170414:111029");
        checkNotNull(databaseInstance.getCreated(), "20170414:111035");
        checkNotNull(databaseInstance.getDatabaseId(), "20170414:111216");
        checkNotNull(databaseInstance.getTemplate(), "20170414:111233");

        Credentials credentials = new Credentials();
        credentials.setPassword(Arrays.toString(databaseInstance.getCredentials().getPassword()));
        credentials.setUsername(databaseInstance.getCredentials().getUsername());

        NetworkBind networkBind = new NetworkBind();
        networkBind.setHost(databaseInstance.getNetworkBind().getHost());
        networkBind.setPort(databaseInstance.getNetworkBind().getPort());

        DatabaseStatus databaseStatus = databaseInstance.getStatus()
            .equals(pl.gov.coi.cascades.server.domain.DatabaseStatus.LAUNCHED)
            ? DatabaseStatus.LAUNCHED
            : DatabaseStatus.DELETED;

        DatabaseInstance instance = new DatabaseInstance();
        instance.setId(databaseIdMapper.toHibernateEntity(databaseInstance.getDatabaseId()));
        instance.setTemplate(templateIdMapper.toHibernateEntity(databaseInstance.getTemplate()));
        instance.setInstanceName(databaseInstance.getInstanceName());
        instance.setReuseTimes(databaseInstance.getReuseTimes());
        instance.setDatabaseName(databaseInstance.getDatabaseName());
        instance.setCredentials(credentials);
        instance.setNetworkBind(networkBind);
        instance.setStatus(databaseStatus);
        instance.setCreated(databaseInstance.getCreated());

        return instance;
    }

    @Override
    public pl.gov.coi.cascades.server.domain.DatabaseInstance fromHibernateEntity(@Nonnull DatabaseInstance databaseInstance) {
        checkNotNull(databaseInstance.getId(), "20170324:155926");
        checkNotNull(databaseInstance.getTemplate(), "20170324:155955");
        checkNotNull(databaseInstance.getType(), "20170324:160730");
        checkNotNull(databaseInstance.getInstanceName(), "20170327:100935");
        checkNotNull(databaseInstance.getReuseTimes(), "20170327:100959");
        checkNotNull(databaseInstance.getDatabaseName(), "20170327:101019");
        checkNotNull(databaseInstance.getCredentials(), "20170327:083701");
        checkNotNull(databaseInstance.getNetworkBind(), "20170327:083729");
        checkNotNull(databaseInstance.getStatus(), "20170327:083800");
        checkNotNull(databaseInstance.getCreated(), "20170327:101053");
        checkNotNull(databaseInstance.getNetworkBind().getPort(), "20170327:084555");

        DatabaseId databaseId = databaseIdMapper.fromHibernateEntity(databaseInstance.getId());
        Template template = templateIdMapper.fromHibernateEntity(databaseInstance.getTemplate());
        UsernameAndPasswordCredentials credentials = new UsernameAndPasswordCredentialsImpl(
            databaseInstance.getCredentials().getUsername(),
            databaseInstance.getCredentials().getPassword().toCharArray()
        );
        pl.gov.coi.cascades.contract.domain.NetworkBind networkBind = new NetworkBindImpl(
            databaseInstance.getNetworkBind().getHost(),
            databaseInstance.getNetworkBind().getPort()
        );
        pl.gov.coi.cascades.server.domain.DatabaseStatus databaseStatus = databaseInstance.getStatus()
            .equals(DatabaseStatus.DELETED)
            ? pl.gov.coi.cascades.server.domain.DatabaseStatus.DELETED
            : pl.gov.coi.cascades.server.domain.DatabaseStatus.LAUNCHED;

        return pl.gov.coi.cascades.server.domain.DatabaseInstance.builder()
            .databaseId(databaseId)
            .template(template)
            .instanceName(databaseInstance.getInstanceName())
            .reuseTimes(databaseInstance.getReuseTimes())
            .databaseName(databaseInstance.getDatabaseName())
            .credentials(credentials)
            .networkBind(networkBind)
            .status(databaseStatus)
            .created(databaseInstance.getCreated())
            .build();
    }

    private static final class NetworkBindImpl implements pl.gov.coi.cascades.contract.domain.NetworkBind {
        private String host;
        private int port;
        private static final long serialVersionUID = 42L;

        NetworkBindImpl(String host, int port) {
            this.host = host;
            this.port = port;
        }

        @Override

        public String getHost() {
            return host;
        }

        @Override
        public int getPort() {
            return port;
        }
    }
}
