package pl.gov.coi.cascades.server.domain;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
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
    private TemplateIdGateway templateIdGateway;

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
    private DatabaseOperations databaseOperations;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testProduceLoadTemplateUseCase() throws Exception {
        // given
        DomainConfiguration domainConfiguration = new DomainConfiguration();

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
        // given
        DomainConfiguration domainConfiguration = new DomainConfiguration();

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
        // given
        DomainConfiguration domainConfiguration = new DomainConfiguration();

        // when
        LaunchNewDatabaseGatewayFacade actual = domainConfiguration.produceGateways(
            templateIdGateway,
            userGateway,
            databaseLimitGateway,
            databaseInstanceGateway,
            databaseOperations
        );

        // then
        assertThat(actual).isInstanceOf(LaunchNewDatabaseGatewayFacade.class);
    }

    @Test
    public void testProduceDeleteLaunchedDatabaseUseCase() throws Exception {
        // given
        DomainConfiguration domainConfiguration = new DomainConfiguration();

        // when
        pl.gov.coi.cascades.server.domain.deletedatabase.UseCase actual = domainConfiguration.produceDeleteLaunchedDatabaseUseCase(
            userGateway,
            databaseIdGateway,
            databaseInstanceGateway
        );

        // then
        assertThat(actual).isInstanceOf(pl.gov.coi.cascades.server.domain.deletedatabase.UseCaseImpl.class);
    }

}
