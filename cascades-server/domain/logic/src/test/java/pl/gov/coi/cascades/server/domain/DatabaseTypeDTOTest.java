package pl.gov.coi.cascades.server.domain;

import lombok.Setter;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.service.Violation;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 05.05.17.
 */
public class DatabaseTypeDTOTest {

    @Mock
    private DatabaseType databaseType;

    @Mock
    private Violation violation;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testOnSuccess() throws Exception {
        // given
        DatabaseTypeDTO databaseTypeDTO = new DatabaseTypeDTO(
            databaseType
        );

        // when
        DatabaseTypeDTO actual = databaseTypeDTO.onSuccess(databaseType1 -> databaseType = databaseType1);
        DatabaseType type = getDatabaseType(actual);
        Violation violation = getError(actual);

        // then
        assertThat(actual).isNotNull();
        assertThat(violation).isNull();
        assertThat(type).isNotNull()
            .isSameAs(databaseType);
    }

    @Test
    public void testOnFail() throws Exception {
        // given
        DatabaseTypeDTO databaseTypeDTO = new DatabaseTypeDTO(
            violation
        );

        // when
        DatabaseTypeDTO actual = databaseTypeDTO.onFail(violation1 -> violation = violation1);
        DatabaseType type = getDatabaseType(actual);
        Violation violation = getError(actual);

        // then
        assertThat(actual).isNotNull();
        assertThat(type).isNull();
        assertThat(violation).isNotNull()
            .isSameAs(violation);
    }

    @Nullable
    private DatabaseType getDatabaseType(DatabaseTypeDTO actual) {
        return new DtoFetcher(actual).getDatabaseType();
    }

    @Nullable
    private Violation getError(DatabaseTypeDTO actual) {
        return new DtoFetcher(actual).getViolation();
    }

    private static final class DtoFetcher {
        @Setter
        private Violation violation;
        @Setter
        private DatabaseType databaseType;

        private DtoFetcher(DatabaseTypeDTO dto) {
            dto.onFail(this::setViolation)
                .onSuccess(this::setDatabaseType)
                .resolve();
        }

        @Nullable
        Violation getViolation() {
            return violation;
        }

        @Nullable
        DatabaseType getDatabaseType() {
            return databaseType;
        }
    }

}
