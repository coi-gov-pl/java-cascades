package pl.gov.coi.cascades.server.domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.TemplateId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 01.03.17.
 */
public class LaunchNewDatabaseInstanceValidatorTest {

    private LaunchNewDatabaseInstanceValidator validator;

    @Mock
    private LaunchNewDatabaseInstanceResponse response;

    @Mock
    private LaunchNewDatabaseInstanceRequest request;

    @Mock
    private DatabaseLimitGateway databaseLimitGateway;

    @Mock
    private DatabaseTypeDTO databaseTypeDTO;

    @Mock
    private TemplateId templateId;

    @Mock
    private User user;

    @Mock
    private DatabaseType databaseType;

    @Mock
    private Error error;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        validator = new LaunchNewDatabaseInstanceValidator(
            response,
            request,
            databaseLimitGateway,
            databaseTypeDTO,
            null,
            user,
            databaseType
        );
    }

    @Test
    public void testValidate() throws Exception {
        // given
        when(response.isSuccessful()).thenReturn(true);
        when(databaseTypeDTO.onFail(any())).thenReturn(databaseTypeDTO);
        when(databaseTypeDTO.onSuccess(any())).thenReturn(databaseTypeDTO);
        doNothing().when(databaseTypeDTO).resolve();
        when(databaseLimitGateway.isGlobalLimitExceeded()).thenReturn(true);
        when(databaseLimitGateway.isPersonalLimitExceeded(user)).thenReturn(true);

        // when
        boolean actual = validator.validate();

        // then
        assertThat(actual)
            .as("Validation result")
            .isTrue();
    }

    @Test
    public void getTemplateId() throws Exception {
        // given
        LaunchNewDatabaseInstanceValidator validator = new LaunchNewDatabaseInstanceValidator(
            response,
            request,
            databaseLimitGateway,
            databaseTypeDTO,
            templateId,
            user,
            databaseType
        );

        // when
        TemplateId actual = validator.getTemplateId();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void getDatabaseType() throws Exception {
        // when
        DatabaseType actual = validator.getDatabaseType();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void getUser() throws Exception {
        // when
        User actual = validator.getUser();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void builder() throws Exception {
        // when
        LaunchNewDatabaseInstanceValidator validatorBuilder = LaunchNewDatabaseInstanceValidator.builder()
            .databaseLimitGateway(databaseLimitGateway)
            .request(request)
            .response(response)
            .databaseTypeDTO(databaseTypeDTO)
            .build();

        // then
        assertThat(validatorBuilder).isNotNull();
    }

}
