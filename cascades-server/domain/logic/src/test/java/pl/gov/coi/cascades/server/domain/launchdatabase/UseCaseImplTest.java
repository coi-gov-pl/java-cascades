package pl.gov.coi.cascades.server.domain.launchdatabase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.ConnectionStringProducer;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;
import pl.gov.coi.cascades.contract.service.Violation;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseLimitGateway;
import pl.gov.coi.cascades.server.domain.DatabaseTypeClassNameService;
import pl.gov.coi.cascades.server.domain.DatabaseTypeDTO;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.persistance.stub.DatabaseIdGatewayStub;
import pl.gov.coi.cascades.server.persistance.stub.UserGatewayStub;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 05.05.17.
 */
public class UseCaseImplTest {

    @Mock
    private DatabaseTypeDTO databaseTypeDTO;

    @Mock
    private LaunchNewDatabaseGatewayFacade launchNewDatabaseGatewayFacade;

    @Mock
    private DatabaseNameGeneratorService databaseNameGeneratorService;

    @Mock
    private UsernameAndPasswordCredentialsGeneratorService credentialsGeneratorService;

    @Mock
    private DatabaseTypeClassNameService databaseTypeClassNameService;

    @Mock
    private DatabaseIdGeneratorService databaseIdGeneratorService;

    @Mock
    private User user;

    @Mock
    private TemplateId id;

    @Mock
    private DatabaseInstance databaseInstance;

    @Mock
    private TemplateIdGateway templateIdGateway;

    @Mock
    private DatabaseId databaseId;

    @Mock
    private DatabaseLimitGateway databaseLimitGateway;

    @Mock
    private DatabaseType databaseType;

    @Mock
    private UsernameAndPasswordCredentials usernameAndPasswordCredentials;

    @Mock
    private Request request;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testExecuteWhenUserIsNotPresent() throws Exception {
        // given
        String templateId = "oracle_template";
        Response response = new UseCaseImplTest.ResponseImpl();
        UseCaseImpl useCase = new UseCaseImpl(
            launchNewDatabaseGatewayFacade,
            databaseNameGeneratorService,
            credentialsGeneratorService,
            databaseTypeClassNameService,
            databaseIdGeneratorService
        );
        when(launchNewDatabaseGatewayFacade.findUser(anyString())).thenReturn(Optional.of(user));
        when(databaseTypeClassNameService.getDatabaseType(anyString())).thenReturn(databaseTypeDTO);
        when(launchNewDatabaseGatewayFacade.getTemplateIdGateway()).thenReturn(templateIdGateway);
        when(templateIdGateway.find(anyString())).thenReturn(Optional.of(id));
        when(request.getTemplateId()).thenReturn(Optional.of(templateId));
        when(request.getUser()).thenReturn(null);

        // when
        expectedException.expectMessage(contains("20170228:164936"));

        // then
        useCase.execute(
            request,
            response
        );
    }

    @Test
    public void testExecuteWhenErrorsOccurred() throws Exception {
        // given
        String message = "Global limit of 0 launched database instances has been reached";
        String path = "databaseLimit.globalLimit";
        String type = "oracle";
        TemplateId id = new TemplateId("", null, false, "");
        String templateId = "oracle_template";
        ResponseImpl response = new ResponseImpl();
        User jrambo = UserGatewayStub.J_RAMBO.addDatabaseInstance(DatabaseIdGatewayStub.INSTANCE1);
        UseCaseImpl useCase = new UseCaseImpl(
            launchNewDatabaseGatewayFacade,
            databaseNameGeneratorService,
            credentialsGeneratorService,
            databaseTypeClassNameService,
            databaseIdGeneratorService
        );
        when(request.getUser()).thenReturn(user);
        when(launchNewDatabaseGatewayFacade.findUser(anyString())).thenReturn(Optional.of(jrambo));
        when(request.getTemplateId()).thenReturn(Optional.of(templateId));
        when(launchNewDatabaseGatewayFacade.getTemplateIdGateway()).thenReturn(templateIdGateway);
        when(templateIdGateway.find(anyString())).thenReturn(Optional.of(id));
        when(launchNewDatabaseGatewayFacade.getDatabaseLimitGateway()).thenReturn(databaseLimitGateway);
        when(databaseLimitGateway.isGlobalLimitExceeded()).thenReturn(true);
        DatabaseTypeDTOExtension databaseTypeDTOStub = new DatabaseTypeDTOExtension(type);
        when(databaseTypeClassNameService.getDatabaseType(anyString())).thenReturn(databaseTypeDTOStub);
        when(databaseTypeDTO.onFail(any())).thenReturn(databaseTypeDTOStub);
        when(databaseTypeDTO.onSuccess(any())).thenReturn(databaseTypeDTOStub);

        // when
        useCase.execute(
            request,
            response
        );

        // then
        verify(launchNewDatabaseGatewayFacade, times(1)).findUser(anyString());
        verify(launchNewDatabaseGatewayFacade, times(1)).getDatabaseLimitGateway();
        verify(launchNewDatabaseGatewayFacade, times(1)).getTemplateIdGateway();
        verify(credentialsGeneratorService, times(0)).generate();
        verify(databaseIdGeneratorService, times(0)).generate();
        verify(launchNewDatabaseGatewayFacade, times(0)).launchDatabase(any(DatabaseInstance.class));
        verify(launchNewDatabaseGatewayFacade, times(0)).save(any(User.class));
        assertThat(response.isSuccessful()).isFalse();
        assertThat(response.violations).isNotEmpty();
        assertThat(response.violations).hasSize(1);
        assertThat(response.violations.iterator().next().getMessage()).contains(message);
        assertThat(response.violations.iterator().next().getPropertyPath()).contains(path);
    }

    @Test
    public void testExecuteWithoutErrors() throws Exception {
        // given
        String templateId = "oracle_template";
        String type = "oracle";
        String instanceName = "ora2424j";
        User jrambo = UserGatewayStub.J_RAMBO.addDatabaseInstance(DatabaseIdGatewayStub.INSTANCE1);
        Response response = new UseCaseImplTest.ResponseImpl();
        UseCaseImpl useCase = new UseCaseImpl(
            launchNewDatabaseGatewayFacade,
            databaseNameGeneratorService,
            credentialsGeneratorService,
            databaseTypeClassNameService,
            databaseIdGeneratorService
        );
        DatabaseTypeDTOExtension databaseTypeDTOStub = new DatabaseTypeDTOExtension(type);
        when(launchNewDatabaseGatewayFacade.findUser(anyString())).thenReturn(Optional.of(jrambo));
        when(databaseTypeClassNameService.getDatabaseType(anyString())).thenReturn(databaseTypeDTOStub);
        when(launchNewDatabaseGatewayFacade.getTemplateIdGateway()).thenReturn(templateIdGateway);
        when(templateIdGateway.find(anyString())).thenReturn(Optional.of(id));
        when(request.getTemplateId()).thenReturn(Optional.of(templateId));
        when(request.getUser()).thenReturn(jrambo);
        when(request.getType()).thenReturn(type);
        when(launchNewDatabaseGatewayFacade.getDatabaseLimitGateway()).thenReturn(databaseLimitGateway);
        when(databaseTypeDTO.onFail(any())).thenReturn(databaseTypeDTOStub);
        when(databaseTypeDTO.onSuccess(any())).thenReturn(databaseTypeDTOStub);
        when(request.getInstanceName()).thenReturn(Optional.of(instanceName));
        when(databaseNameGeneratorService.generate(anyString())).thenReturn(instanceName);
        when(credentialsGeneratorService.generate()).thenReturn(usernameAndPasswordCredentials);
        when(databaseIdGeneratorService.generate()).thenReturn(databaseId);
        when(launchNewDatabaseGatewayFacade.launchDatabase(any(DatabaseInstance.class))).thenReturn(databaseInstance);

        // when
        useCase.execute(
            request,
            response
        );

        // then
        verify(launchNewDatabaseGatewayFacade, times(1)).findUser(anyString());
        verify(launchNewDatabaseGatewayFacade, times(1)).getDatabaseLimitGateway();
        verify(launchNewDatabaseGatewayFacade, times(1)).getTemplateIdGateway();
        verify(credentialsGeneratorService, times(1)).generate();
        verify(databaseIdGeneratorService, times(1)).generate();
        verify(launchNewDatabaseGatewayFacade, times(1)).launchDatabase(any(DatabaseInstance.class));
        verify(launchNewDatabaseGatewayFacade, times(1)).save(any(User.class));
        assertThat(response.isSuccessful()).isTrue();
    }

    @Test
    public void testExecuteWithInstanceNameNotPresent() throws Exception {
        // given
        String templateId = "oracle_template";
        String type = "oracle";
        String instanceName = "ora2424j";
        User jrambo = UserGatewayStub.J_RAMBO.addDatabaseInstance(DatabaseIdGatewayStub.INSTANCE1);
        Response response = new UseCaseImplTest.ResponseImpl();
        UseCaseImpl useCase = new UseCaseImpl(
            launchNewDatabaseGatewayFacade,
            databaseNameGeneratorService,
            credentialsGeneratorService,
            databaseTypeClassNameService,
            databaseIdGeneratorService
        );
        DatabaseTypeDTOExtension databaseTypeDTOStub = new DatabaseTypeDTOExtension(type);
        when(launchNewDatabaseGatewayFacade.findUser(anyString())).thenReturn(Optional.of(jrambo));
        when(databaseTypeClassNameService.getDatabaseType(anyString())).thenReturn(databaseTypeDTOStub);
        when(launchNewDatabaseGatewayFacade.getTemplateIdGateway()).thenReturn(templateIdGateway);
        when(templateIdGateway.find(anyString())).thenReturn(Optional.of(id));
        when(request.getTemplateId()).thenReturn(Optional.of(templateId));
        when(request.getUser()).thenReturn(jrambo);
        when(request.getType()).thenReturn(type);
        when(launchNewDatabaseGatewayFacade.getDatabaseLimitGateway()).thenReturn(databaseLimitGateway);
        when(databaseTypeDTO.onFail(any())).thenReturn(databaseTypeDTOStub);
        when(databaseTypeDTO.onSuccess(any())).thenReturn(databaseTypeDTOStub);
        when(request.getInstanceName()).thenReturn(Optional.empty());
        when(databaseNameGeneratorService.generate(anyString())).thenReturn(instanceName);
        when(credentialsGeneratorService.generate()).thenReturn(usernameAndPasswordCredentials);
        when(databaseIdGeneratorService.generate()).thenReturn(databaseId);
        when(launchNewDatabaseGatewayFacade.launchDatabase(any(DatabaseInstance.class))).thenReturn(databaseInstance);

        // when
        useCase.execute(
            request,
            response
        );

        // then
        verify(launchNewDatabaseGatewayFacade, times(1)).findUser(anyString());
        verify(launchNewDatabaseGatewayFacade, times(1)).getDatabaseLimitGateway();
        verify(launchNewDatabaseGatewayFacade, times(1)).getTemplateIdGateway();
        verify(credentialsGeneratorService, times(1)).generate();
        verify(databaseIdGeneratorService, times(1)).generate();
        verify(launchNewDatabaseGatewayFacade, times(1)).launchDatabase(any(DatabaseInstance.class));
        verify(launchNewDatabaseGatewayFacade, times(1)).save(any(User.class));
        assertThat(response.isSuccessful()).isTrue();
    }

    @Test
    public void testBuilder() throws Exception {
        // when
        UseCaseImpl useCase = UseCaseImpl.builder()
            .databaseIdGeneratorService(databaseIdGeneratorService)
            .launchNewDatabaseGatewayFacade(launchNewDatabaseGatewayFacade)
            .credentialsGeneratorService(credentialsGeneratorService)
            .databaseNameGeneratorService(databaseNameGeneratorService)
            .databaseTypeClassNameService(databaseTypeClassNameService)
            .build();

        // then
        assertThat(useCase).isNotNull();
    }

    private static final class DatabaseTypeDTOExtension extends DatabaseTypeDTO {
        private DatabaseTypeDTOExtension(String type) {
            super(new DatabaseType() {

                @Override
                public String getName() {
                    return type;
                }

                @Override
                public ConnectionStringProducer getConnectionStringProducer() {
                    return null;
                }
            });
        }
    }

    private static final class ResponseImpl implements Response {

        private final Collection<Violation> violations = new HashSet<>();
        private DatabaseId databaseId;
        private NetworkBind networkBind;
        private UsernameAndPasswordCredentials credentials;
        private String databaseName;

        @Override
        public boolean isSuccessful() {
            return violations.isEmpty();
        }

        @Override
        public void addError(Violation violation) {
            violations.add(violation);
        }

        @Override
        public void setDatabaseId(DatabaseId databaseId) {
            this.databaseId = databaseId;
        }

        @Override
        public void setNetworkBind(NetworkBind networkBind) {
            this.networkBind = networkBind;
        }

        @Override
        public void setCredentials(UsernameAndPasswordCredentials credentials) {
            this.credentials = credentials;
        }

        @Override
        public void setDatabaseName(String databaseName) {
            this.databaseName = databaseName;
        }

    }

}
