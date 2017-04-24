package pl.gov.coi.cascades.server.domain.deletedatabase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.User;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 22.03.17.
 */
public class ValidatorTest {

    @Mock
    private Response response;

    @Mock
    private Request request;

    @Mock
    private DatabaseId databaseId1, databaseId2;

    @Mock
    private User user;

    @Mock
    private DatabaseInstance databaseInstance1, databaseInstance2;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testValidate() throws Exception {
        // given
        Validator validator = new Validator(
            response,
            request,
            databaseId1,
            user
        );
        when(response.isSuccessful()).thenReturn(true);

        // when
        boolean actual = validator.validate();

        // then
        assertThat(actual)
            .as("Validation result")
            .isTrue();
    }

    @Test
    public void testValidateWhenUserHasDatabases() throws Exception {
        // given
        Validator validator = new Validator(
            response,
            request,
            databaseId1,
            user
        );
        Collection<DatabaseInstance> databases = new ArrayList<>();
        when(databaseInstance1.getDatabaseId()).thenReturn(databaseId1);
        when(databaseInstance1.getDatabaseId().getId()).thenReturn("pos34t56");
        databases.add(databaseInstance1);
        when(databaseInstance2.getDatabaseId()).thenReturn(databaseId2);
        when(databaseInstance2.getDatabaseId().getId()).thenReturn("pos34t56");
        databases.add(databaseInstance2);
        when(response.isSuccessful()).thenReturn(true);
        when(user.getDatabases()).thenReturn(databases);

        // when
        boolean actual = validator.validate();

        // then
        assertThat(actual)
            .as("Validation result")
            .isTrue();
    }

    @Test
    public void testValidateWhenDatabaseIdNotEquals() throws Exception {
        // given
        Validator validator = new Validator(
            response,
            request,
            databaseId1,
            user
        );
        Collection<DatabaseInstance> databases = new ArrayList<>();
        when(databaseInstance1.getDatabaseId()).thenReturn(databaseId2);
        when(databaseInstance1.getDatabaseId().getId()).thenReturn("pos12t34");
        databases.add(databaseInstance1);
        when(user.getDatabases()).thenReturn(databases);

        // when
        boolean actual = validator.validate();

        // then
        assertThat(actual).isFalse();
    }

    @Test
    public void testValidateWhenNotPresentDatabaseId() throws Exception {
        // given
        Validator validator = new Validator(
            response,
            request,
            null,
            null
        );
        when(response.isSuccessful()).thenReturn(true);

        // when
        boolean actual = validator.validate();

        // then
        assertThat(actual)
            .as("Validation result")
            .isTrue();
    }

    @Test
    public void testGetUser() throws Exception {
        // when
        Validator validator = new Validator(
            response,
            request,
            databaseId1,
            user
        );
        User actual = validator.getUser();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testBuilder() throws Exception {
        // when
        Validator validatorBuilder = Validator.builder()
            .request(request)
            .response(response)
            .build();

        // then
        assertThat(validatorBuilder).isNotNull();
    }

}
