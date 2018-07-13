package pl.gov.coi.cascades.server.domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.server.domain.deletedatabase.DeleteDatabaseGatewayFacade;
import pl.gov.coi.cascades.server.domain.launchdatabase.DatabaseIdGeneratorService;
import pl.gov.coi.cascades.server.domain.launchdatabase.DatabaseNameGeneratorService;
import pl.gov.coi.cascades.server.domain.launchdatabase.LaunchNewDatabaseGatewayFacade;
import pl.gov.coi.cascades.server.domain.launchdatabase.UseCase;
import pl.gov.coi.cascades.server.domain.launchdatabase.UseCaseImpl;
import pl.gov.coi.cascades.server.domain.launchdatabase.UsernameAndPasswordCredentialsGeneratorService;
import pl.gov.coi.cascades.server.domain.loadtemplate.TemplateIdGeneratorService;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.04.17.
 */
public class DomainConfigurationTest {

    private DomainConfiguration domainConfiguration;

    @Mock
    private LaunchNewDatabaseGatewayFacade launchNewDatabaseGatewayFacade;

    @Mock
    private DeleteDatabaseGatewayFacade deleteDatabaseGatewayFacade;

    @Mock
    private DatabaseNameGeneratorService databaseNameGeneratorService;

    @Mock
    private UsernameAndPasswordCredentialsGeneratorService credentialsGeneratorService;

    @Mock
    private DatabaseTypeClassNameService databaseTypeClassNameService;

    @Mock
    private DatabaseIdGeneratorService databaseIdGeneratorService;

    @Mock
    private TemplateIdGateway templateIdGateway;

    @Mock
    private DatabaseUserGateway databaseUserGateway;

    @Mock
    private DatabaseTemplateGateway databaseTemplateGateway;

    @Mock
    private UserGateway userGateway;

    @Mock
    private DatabaseLimitGateway databaseLimitGateway;

    @Mock
    private DatabaseInstanceGateway databaseInstanceGateway;

    @Mock
    private DatabaseIdGateway databaseIdGateway;

    @Mock
    private TemplateIdGeneratorService templateIdGeneratorService;

    @Mock
    private DatabaseOperationsGateway databaseOperationsGateway;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void init() {
       domainConfiguration = new DomainConfiguration();
    }

    @Test
    public void testProduceLoadTemplateUseCase() throws Exception {
        // when
        pl.gov.coi.cascades.server.domain.loadtemplate.UseCase actual = domainConfiguration.produceLoadTemplateUseCase(
            templateIdGateway,
            databaseTemplateGateway,
            templateIdGeneratorService
        );

        // then
        assertThat(actual).isInstanceOf(pl.gov.coi.cascades.server.domain.loadtemplate.UseCaseImpl.class);
    }

    @Test
    public void testProduceLaunchNewDatabaseUseCase() throws Exception {
        // when
        UseCase actual = domainConfiguration.produceLaunchNewDatabaseUseCase(
            launchNewDatabaseGatewayFacade,
            databaseNameGeneratorService,
            credentialsGeneratorService,
            databaseTypeClassNameService,
            databaseIdGeneratorService
        );

        // then
        assertThat(actual).isInstanceOf(UseCaseImpl.class);
    }

    @Test
    public void testProduceGateways() throws Exception {
        // when
        LaunchNewDatabaseGatewayFacade actual = domainConfiguration.produceGateways(
            templateIdGateway,
            userGateway,
            databaseLimitGateway,
            databaseInstanceGateway,
            databaseOperationsGateway,
            databaseUserGateway
        );

        // then
        assertThat(actual).isInstanceOf(LaunchNewDatabaseGatewayFacade.class);
    }

    @Test
    public void shouldDeleteDatabaseGateways() {
        // when
        DeleteDatabaseGatewayFacade actual = domainConfiguration.produceDeleteDatabaseGateways(
            userGateway,
            databaseIdGateway,
            databaseInstanceGateway,
            databaseOperationsGateway,
            databaseUserGateway
        );

        // then
        assertThat(actual).isInstanceOf(DeleteDatabaseGatewayFacade.class);
    }

    @Test
    public void testProduceDeleteLaunchedDatabaseUseCase() throws Exception {
        // when
        pl.gov.coi.cascades.server.domain.deletedatabase.UseCase actual = domainConfiguration.produceDeleteLaunchedDatabaseUseCase(
            deleteDatabaseGatewayFacade
        );

        // then
        assertThat(actual).isInstanceOf(pl.gov.coi.cascades.server.domain.deletedatabase.UseCaseImpl.class);
    }

}
