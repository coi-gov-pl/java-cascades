package pl.gov.coi.cascades.server.persistance.hibernate.mappers;

import lombok.Setter;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;
import pl.gov.coi.cascades.server.domain.DatabaseTypeClassNameService;
import pl.gov.coi.cascades.server.domain.DatabaseTypeDTO;
import pl.gov.coi.cascades.server.domain.Error;
import pl.gov.coi.cascades.server.domain.launchdatabase.UsernameAndPasswordCredentialsImpl;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance;

import javax.annotation.Nullable;
import javax.inject.Inject;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.03.17.
 */
public class DatabaseInstanceMapper implements Mapper<DatabaseInstance, pl.gov.coi.cascades.server.domain.DatabaseInstance> {

    private final DatabaseTypeClassNameService databaseTypeClassNameService;

    @Inject
    public DatabaseInstanceMapper(DatabaseTypeClassNameService databaseTypeClassNameService) {
        this.databaseTypeClassNameService = databaseTypeClassNameService;
    }

    @Override
    public DatabaseInstance toHibernateEntity(pl.gov.coi.cascades.server.domain.DatabaseInstance databaseInstance) {
        return null;
    }

    @Override
    public pl.gov.coi.cascades.server.domain.DatabaseInstance fromHibernateEntity(DatabaseInstance databaseInstance) {
        DatabaseId databaseId = new DatabaseId(databaseInstance.getDatabaseId());
        TemplateId templateId = new TemplateId(databaseInstance.getTemplateId());
        DatabaseTypeDTO databaseTypeDTO = databaseTypeClassNameService.getDatabaseType(databaseInstance.getDatabaseType());
        DatabaseType databaseType = new DtoFetcher(databaseTypeDTO).getDatabaseType();
        UsernameAndPasswordCredentials credentials = new UsernameAndPasswordCredentialsImpl(
            databaseInstance.getCredentials().getUsername(),
            databaseInstance.getCredentials().getPassword().toCharArray()
        );
        return null;
/*
        return new pl.gov.coi.cascades.server.domain.DatabaseInstance(
            databaseId,
            templateId,
            databaseType,
            databaseInstance.getInstanceName(),
            databaseInstance.getReuseTimes(),
            databaseInstance.getDatabaseName(),
            credentials,
            databaseInstance.getStatus(),
            databaseInstance.getCreated()
        );
        */
    }

    private static final class DtoFetcher {
        @Setter
        private Error error;
        @Setter
        private DatabaseType databaseType;

        private DtoFetcher(DatabaseTypeDTO dto) {
            dto.onFail(this::setError)
                .onSuccess(this::setDatabaseType)
                .resolve();
        }

        @Nullable
        Error getError() {
            return error;
        }

        @Nullable
        DatabaseType getDatabaseType() {
            return databaseType;
        }
    }

}
