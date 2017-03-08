package pl.gov.coi.cascades.server.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.DatabaseType;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 08.03.17.
 */
public class DatabaseTypeClassNameServiceTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testGetDatabaseTypeNotPresentClassName() throws Exception {
        // given
        String notExistingClass = "pl.gov.coi.cascades.server.domain.Database";
        String message = String.format("Given class: %s is not available.", notExistingClass);
        DatabaseTypeClassNameService databaseTypeClassNameService = new DatabaseTypeClassNameService();

        // when
        DatabaseTypeDTO actual = databaseTypeClassNameService.getDatabaseType(notExistingClass);
        Error error = getPrivateField(actual, "error", Error.class);

        // then
        assertThat(actual).isNotNull();
        assertThat(error.getMessage()).contains(message);
    }

    @Test
    public void testGetDatabaseNotRepresentDatabaseType() throws Exception {
        // given
        String existingClass = "pl.gov.coi.cascades.server.domain.DatabaseIdGeneratorService";
        String message = String.format(
            "Given class: %s is not subclass of %s",
            existingClass,
            DatabaseType.class.getName()
        );
        DatabaseTypeClassNameService databaseTypeClassNameService = new DatabaseTypeClassNameService();

        // when
        DatabaseTypeDTO actual = databaseTypeClassNameService.getDatabaseType(existingClass);
        Error error = getPrivateField(actual, "error", Error.class);

        // then
        assertThat(actual).isNotNull();
        assertThat(error.getMessage()).contains(message);
    }

    @Test
    public void testGetDatabaseRepresentDatabaseType() throws Exception {
        // given
        String existingClass = "pl.gov.coi.cascades.server.persistance.stub.DatabaseTypeStub";
        DatabaseTypeClassNameService databaseTypeClassNameService = new DatabaseTypeClassNameService();

        // when
        DatabaseTypeDTO actual = databaseTypeClassNameService.getDatabaseType(existingClass);

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testGetDatabaseNotInstantiate() throws Exception {
        // given
        String existingClass = "pl.gov.coi.cascades.server.domain.DatabaseTypeImplTest";
        String message = String.format(
            "Given class: %s can not be instantiated. It must be public with public no arguments constructor.",
            existingClass
        );
        DatabaseTypeClassNameService databaseTypeClassNameService = new DatabaseTypeClassNameService();

        // when
        DatabaseTypeDTO actual = databaseTypeClassNameService.getDatabaseType(existingClass);
        Error error = getPrivateField(actual, "error", Error.class);

        // then
        assertThat(actual).isNotNull();
        assertThat(error.getMessage()).contains(message);
    }

    private static <T> T getPrivateField(Object object,
                                         String fieldName,
                                         Class<T> fieldType)
        throws NoSuchFieldException, IllegalAccessException {
        Class<?> cls = object.getClass();
        Field field = cls.getDeclaredField(fieldName);
        field.setAccessible(true);
        return fieldType.cast(field.get(object));
    }

}
