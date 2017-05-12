package pl.gov.coi.cascades.server.presentation;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.server.OsgiBeanLocator;
import pl.gov.coi.cascades.server.domain.DatabaseTypeClassNameService;
import pl.gov.coi.cascades.server.domain.launchdatabase.DatabaseIdGeneratorService;
import pl.gov.coi.cascades.server.domain.launchdatabase.DatabaseNameGeneratorService;
import pl.gov.coi.cascades.server.domain.launchdatabase.UsernameAndPasswordCredentialsGeneratorService;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.04.17.
 */
public class PresentationConfigurationTest {

    @Mock
    private OsgiBeanLocator osgiBeanLocator;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testProduceDatabaseIdGeneratorService() throws Exception {
        // given
        PresentationConfiguration presentationConfiguration = new PresentationConfiguration();

        // when
        DatabaseIdGeneratorService actual = presentationConfiguration.produceDatabaseIdGeneratorService();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isInstanceOf(DatabaseIdGeneratorService.class);
    }

    @Test
    public void testProduceDatabaseNameGeneratorService() throws Exception {
        // given
        PresentationConfiguration presentationConfiguration = new PresentationConfiguration();

        // when
        DatabaseNameGeneratorService actual = presentationConfiguration.produceDatabaseNameGeneratorService();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isInstanceOf(DatabaseNameGeneratorService.class);
    }

    @Test
    public void testProduceCredentials() throws Exception {
        // given
        PresentationConfiguration presentationConfiguration = new PresentationConfiguration();

        // when
        UsernameAndPasswordCredentialsGeneratorService actual = presentationConfiguration.produceCredentials();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isInstanceOf(UsernameAndPasswordCredentialsGeneratorService.class);
    }

    @Test
    public void testProduceDatabaseTypeClassName() throws Exception {
        // given
        PresentationConfiguration presentationConfiguration = new PresentationConfiguration();

        // when
        DatabaseTypeClassNameService actual = presentationConfiguration.produceDatabaseTypeClassName(
            osgiBeanLocator
        );

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isInstanceOf(DatabaseTypeClassNameService.class);
    }

}
