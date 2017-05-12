package pl.gov.coi.cascades.server.domain.launchdatabase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.contract.service.Violation;
import pl.gov.coi.cascades.server.domain.DatabaseLimitGateway;
import pl.gov.coi.cascades.server.domain.DatabaseTypeDTO;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;
import pl.gov.coi.cascades.server.domain.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 01.03.17.
 */
public class ValidatorTest {

    private Validator validator;

    @Mock
    private Response response;

    @Mock
    private Request request;

    @Mock
    private DatabaseLimitGateway databaseLimitGateway;

    @Mock
    private TemplateIdGateway templateIdGateway;

    @Mock
    private DatabaseTypeDTO databaseTypeDTO;

    @Mock
    private TemplateId templateId;

    @Mock
    private User user;

    @Mock
    private DatabaseType databaseType;

    @Mock
    private Violation violation;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        validator = new Validator(
            response,
            request,
            databaseLimitGateway,
            templateIdGateway,
            databaseTypeDTO,
            null,
            user,
            databaseType
        );
    }

    @Test
    public void testValidateWhenTemplateIdIsNotGiven() {
        // given
        when(response.isSuccessful()).thenReturn(true);
        when(request.getTemplateId()).thenReturn(Optional.empty());
        when(templateIdGateway.getDefaultTemplateId()).thenReturn(Optional.empty());
        when(databaseTypeDTO.onFail(any())).thenReturn(databaseTypeDTO);
        when(databaseTypeDTO.onSuccess(any())).thenReturn(databaseTypeDTO);
        doNothing().when(databaseTypeDTO).resolve();

        // when
        boolean actual = validator.validate();

        // then
        assertThat(actual)
            .as("Validation result")
            .isTrue();
    }

    @Test
    public void testValidateWhenTemplateIdIsGivenButNotPresent() {
        // given
        String id = "oracle_template";
        when(request.getTemplateId()).thenReturn(Optional.of(id));
        when(templateIdGateway.getDefaultTemplateId()).thenReturn(Optional.empty());
        when(templateIdGateway.find(anyString())).thenReturn(Optional.empty());
        when(databaseTypeDTO.onFail(any())).thenReturn(databaseTypeDTO);
        when(databaseTypeDTO.onSuccess(any())).thenReturn(databaseTypeDTO);
        doNothing().when(databaseTypeDTO).resolve();

        // when
        boolean actual = validator.validate();

        // then
        assertThat(actual)
            .as("Validation result")
            .isFalse();
    }

    @Test
    public void testValidateWhenTemplateIdIsGivenAndPresent() {
        // given
        String id = "oracle_template";
        when(request.getTemplateId()).thenReturn(Optional.of(id));
        when(templateIdGateway.getDefaultTemplateId()).thenReturn(Optional.empty());
        when(templateIdGateway.find(anyString())).thenReturn(Optional.of(templateId));
        when(databaseTypeDTO.onFail(any())).thenReturn(databaseTypeDTO);
        when(databaseTypeDTO.onSuccess(any())).thenReturn(databaseTypeDTO);
        doNothing().when(databaseTypeDTO).resolve();

        // when
        boolean actual = validator.validate();

        // then
        assertThat(actual)
            .as("Validation result")
            .isFalse();
    }

    @Test
    public void testValidate() throws Exception {
        // given
        when(response.isSuccessful()).thenReturn(true);
        when(request.getTemplateId()).thenReturn(Optional.empty());
        when(databaseTypeDTO.onFail(any())).thenReturn(databaseTypeDTO);
        when(databaseTypeDTO.onSuccess(any())).thenReturn(databaseTypeDTO);
        doNothing().when(databaseTypeDTO).resolve();
        when(databaseLimitGateway.isGlobalLimitExceeded()).thenReturn(true);
        when(databaseLimitGateway.isPersonalLimitExceeded(user)).thenReturn(true);
        when(templateIdGateway.getDefaultTemplateId()).thenReturn(Optional.of(templateId));

        // when
        boolean actual = validator.validate();

        // then
        assertThat(actual)
            .as("Validation result")
            .isTrue();
    }

    @Test
    public void testValidateWhenUserIsInvalid() throws Exception {
        // given
        Validator validator = new Validator(
            response,
            request,
            databaseLimitGateway,
            templateIdGateway,
            databaseTypeDTO,
            null,
            null,
            databaseType
        );
        when(request.getTemplateId()).thenReturn(Optional.empty());
        when(databaseTypeDTO.onFail(any())).thenReturn(databaseTypeDTO);
        when(databaseTypeDTO.onSuccess(any())).thenReturn(databaseTypeDTO);
        doNothing().when(databaseTypeDTO).resolve();
        when(databaseLimitGateway.isGlobalLimitExceeded()).thenReturn(true);
        when(databaseLimitGateway.isPersonalLimitExceeded(user)).thenReturn(true);
        when(templateIdGateway.getDefaultTemplateId()).thenReturn(Optional.of(templateId));

        // then
        expectedException.expectMessage(contains("20170228:164936"));

        // when
        validator.validate();
    }

    @Test
    public void testValidateWithExceededLimitOfDatabases() throws Exception {
        // given
        when(request.getTemplateId()).thenReturn(Optional.empty());
        when(databaseTypeDTO.onFail(any())).thenReturn(databaseTypeDTO);
        when(databaseTypeDTO.onSuccess(any())).thenReturn(databaseTypeDTO);
        doNothing().when(databaseTypeDTO).resolve();
        when(databaseLimitGateway.isGlobalLimitExceeded()).thenReturn(false);
        when(databaseLimitGateway.isPersonalLimitExceeded(user)).thenReturn(false);
        when(templateIdGateway.getDefaultTemplateId()).thenReturn(Optional.of(templateId));

        // when
        boolean actual = validator.validate();

        // then
        assertThat(actual)
            .as("Validation result")
            .isFalse();
    }

    @Test
    public void testGetTemplateId() throws Exception {
        // given
        Validator validator = new Validator(
            response,
            request,
            databaseLimitGateway,
            templateIdGateway,
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
    public void testGetDatabaseType() throws Exception {
        // when
        DatabaseType actual = validator.getDatabaseType();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testGetUser() throws Exception {
        // when
        User actual = validator.getUser();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testBuilder() throws Exception {
        // when
        Validator validatorBuilder = Validator.builder()
            .databaseLimitGateway(databaseLimitGateway)
            .request(request)
            .response(response)
            .databaseTypeDTO(databaseTypeDTO)
            .build();

        // then
        assertThat(validatorBuilder).isNotNull();
    }

}
