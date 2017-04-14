package pl.gov.coi.cascades.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;

import javax.inject.Inject;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 06.04.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@HibernateDevelopmentTest
public class TemplateIdGatewayImplFunctionalIT {

    private static final String NON_EXISTING_TEMPLATE_ID = "875785887";
    private static final String EXISTING_TEMPLATE_ID = "1";
    private TemplateIdGateway templateIdGateway;

    @Inject
    public void setDatabaseIdGateway(TemplateIdGateway templateIdGateway) {
        this.templateIdGateway = templateIdGateway;
    }

    @Test
    public void testFindPositivePath() throws Exception {
        // when
        Optional<TemplateId> actual = templateIdGateway.find(EXISTING_TEMPLATE_ID);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.isPresent()).isTrue();
    }

    @Test
    public void testFindNegativePath() throws Exception {
        // when
        Optional<TemplateId> actual = templateIdGateway.find(NON_EXISTING_TEMPLATE_ID);

        // then
        assertThat(actual).isEmpty();
    }

    @Test
    public void testGetDefaultTemplateIdPositivePath() throws Exception {
        // when
        Optional<TemplateId> actual = templateIdGateway.getDefaultTemplateId();

        // then
        assertThat(actual).isNotNull();
    }

}
