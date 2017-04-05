package pl.gov.coi.cascades.server.domain;

import lombok.Setter;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.slf4j.Logger;
import pl.gov.coi.cascades.contract.domain.ConnectionStringProducer;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.service.Violation;
import pl.gov.coi.cascades.server.OsgiBeanLocator;
import pl.gov.coi.cascades.server.persistance.stub.DatabaseTypeStub;

import javax.annotation.Nullable;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.gov.coi.cascades.server.domain.OsgiDatabaseTypeClassNameService.ERROR_MESSAGE_FORMAT;
import static pl.gov.coi.cascades.server.domain.OsgiDatabaseTypeClassNameService.FOUND_DATABASE_TYPE_LOG_FORMAT;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 08.03.17.
 */
public class OsgiDatabaseTypeClassNameServiceTest {

    private static final DatabaseType STUB_DATABASE_TYPE = new DatabaseTypeStub();
    private static final Iterable<DatabaseType> DATABASE_TYPES = Collections.singletonList(
        STUB_DATABASE_TYPE
    );

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private OsgiBeanLocator locator;

    @Mock
    private Logger logger;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void before() {
        when(locator.getBeans(DatabaseType.class)).thenReturn(DATABASE_TYPES);
        when(logger.isDebugEnabled()).thenReturn(true);
    }

    @After
    public void after() {
        verify(locator, times(1)).getBeans(DatabaseType.class);
    }

    @Test
    public void testGetDatabaseTypeNotPresentClassName() throws Exception {
        // given
        String search = "org.example.NonExisting";
        String message = String.format(ERROR_MESSAGE_FORMAT, search);
        DatabaseTypeClassNameService databaseTypeClassNameService =
            new OsgiDatabaseTypeClassNameService(locator);

        // when
        DatabaseTypeDTO actual = databaseTypeClassNameService.getDatabaseType(search);
        DatabaseType databaseType = getDatabaseType(actual);
        Violation violation = getError(actual);

        // then
        assertThat(actual).isNotNull();
        assertThat(databaseType).isNull();
        assertThat(violation).isNotNull();
        assertThat(violation.getMessage()).contains(message);
    }

    @Test
    public void testGetDatabaseByFQCN() throws Exception {
        // given
        String search = DatabaseTypeStub.class.getName();
        DatabaseTypeClassNameService databaseTypeClassNameService =
            new OsgiDatabaseTypeClassNameService(locator, logger);

        // when
        DatabaseTypeDTO actual = databaseTypeClassNameService.getDatabaseType(search);
        DatabaseType databaseType = getDatabaseType(actual);
        Violation violation = getError(actual);

        // then
        assertThat(actual).isNotNull();
        assertThat(violation).isNull();
        assertThat(databaseType).isNotNull()
            .isSameAs(STUB_DATABASE_TYPE);
    }

    @Test
    public void testGetDatabaseByName() throws Exception {
        // given
        String search = "stub";
        DatabaseTypeClassNameService databaseTypeClassNameService =
            new OsgiDatabaseTypeClassNameService(locator, logger);

        // when
        DatabaseTypeDTO actual = databaseTypeClassNameService.getDatabaseType(search);
        DatabaseType databaseType = getDatabaseType(actual);
        Violation violation = getError(actual);

        // then
        assertThat(actual).isNotNull();
        assertThat(violation).isNull();
        assertThat(databaseType).isNotNull()
            .isSameAs(STUB_DATABASE_TYPE);
        verify(logger, times(1))
            .isDebugEnabled();
        verify(logger, times(1))
            .debug(
                FOUND_DATABASE_TYPE_LOG_FORMAT,
                DatabaseTypeStub.class.getName(),
                search
            );
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

    /**
     * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
     * @since 08.03.17.
     */
    public static class PublicDatabaseTypeButWithOneArgConstructor implements DatabaseType {

        private final String name;

        public PublicDatabaseTypeButWithOneArgConstructor(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public ConnectionStringProducer getConnectionStringProducer() {
            return null;
        }
    }

}
