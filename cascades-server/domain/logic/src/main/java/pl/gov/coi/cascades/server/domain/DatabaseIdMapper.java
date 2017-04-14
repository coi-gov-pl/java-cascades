package pl.gov.coi.cascades.server.domain;

import pl.gov.coi.cascades.contract.domain.DatabaseId;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 04.04.17.
 */
public class DatabaseIdMapper implements Mapper<Long, DatabaseId> {

    private static final int BASE_36 = 36;

    @Override
    public Long toHibernateEntity(DatabaseId databaseId) {
        return Long.parseLong(databaseId.getId(), BASE_36);
    }

    @Override
    public DatabaseId fromHibernateEntity(Long id) {
        return new DatabaseId(
            Long.toString(id, BASE_36)
        );
    }

}
