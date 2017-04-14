package pl.gov.coi.cascades.server.domain;

import org.junit.Test;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.server.domain.DatabaseIdMapper;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 04.04.17.
 */
public class DatabaseIdMapperTest {

    private static final int BASE_36 = 36;

    @Test
    public void testToHibernateEntity() throws Exception {
        // given
        DatabaseIdMapper databaseIdMapper = new DatabaseIdMapper();
        String id = "213124421";
        DatabaseId databaseId = new DatabaseId(id);

        // when
        Long actual = databaseIdMapper.toHibernateEntity(databaseId);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(Long.parseLong(id, BASE_36));
    }

    @Test
    public void testFromHibernateEntity() throws Exception {
        // given
        DatabaseIdMapper databaseIdMapper = new DatabaseIdMapper();
        Long id = 213124421L;

        // when
        DatabaseId actual = databaseIdMapper.fromHibernateEntity(id);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(Long.toString(id, BASE_36));
    }

}
