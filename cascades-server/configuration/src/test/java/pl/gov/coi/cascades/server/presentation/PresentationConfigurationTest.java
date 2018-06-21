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
import pl.gov.coi.cascades.server.domain.loadtemplate.TemplateIdGeneratorService;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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

    private final PresentationConfiguration presentationConfiguration = new PresentationConfiguration();

    @Test
    public void testProduceTemplateIdGeneratorService() {
        // when
        TemplateIdGeneratorService actual = presentationConfiguration.produceTemplateIdGeneratorService();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isInstanceOf(TemplateIdGeneratorService.class);
    }

    @Test
    public void testProduceDatabaseIdGeneratorService() {
        // when
        DatabaseIdGeneratorService actual = presentationConfiguration.produceDatabaseIdGeneratorService();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isInstanceOf(DatabaseIdGeneratorService.class);
    }

    @Test
    public void testProduceDatabaseNameGeneratorService() {
        // when
        DatabaseNameGeneratorService actual = presentationConfiguration.produceDatabaseNameGeneratorService();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isInstanceOf(DatabaseNameGeneratorService.class);
    }

    @Test
    public void testProduceCredentials() {
        // when
        UsernameAndPasswordCredentialsGeneratorService actual = presentationConfiguration.produceCredentials(new SecureRandom());

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isInstanceOf(UsernameAndPasswordCredentialsGeneratorService.class);
    }

    @Test
    public void testProduceRandomGenerator() {
        // when
        Random actual = presentationConfiguration.produceRandomGenerator();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isInstanceOf(ThreadLocalRandom.class);
    }

    @Test
    public void testProduceDatabaseTypeClassName() {
        // when
        DatabaseTypeClassNameService actual = presentationConfiguration.produceDatabaseTypeClassName(
            osgiBeanLocator
        );

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isInstanceOf(DatabaseTypeClassNameService.class);
    }

}
