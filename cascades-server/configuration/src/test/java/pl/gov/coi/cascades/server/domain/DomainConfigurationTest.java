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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.04.17.
 */
public class DomainConfigurationTest {

    @Mock
    LaunchNewDatabaseGatewayFacade launchNewDatabaseGatewayFacade;

    @Mock
    DatabaseNameGeneratorService databaseNameGeneratorService;

    @Mock
    UsernameAndPasswordCredentialsGeneratorService credentialsGeneratorService;

    @Mock
    DatabaseTypeClassNameService databaseTypeClassNameService;

    @Mock
    DatabaseIdGeneratorService databaseIdGeneratorService;

    @Mock
    TemplateIdGateway templateIdGateway;

    @Mock
    UserGateway userGateway;

    @Mock
    DatabaseLimitGateway databaseLimitGateway;

    @Mock
    DatabaseInstanceGateway databaseInstanceGateway;

    @Mock
    DatabaseIdGateway databaseIdGateway;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

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
            databaseInstanceGateway
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
