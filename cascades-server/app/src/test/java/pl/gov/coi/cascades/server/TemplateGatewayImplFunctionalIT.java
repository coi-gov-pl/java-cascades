package pl.gov.coi.cascades.server;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.contract.domain.TemplateIdStatus;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;
import pl.gov.coi.cascades.server.persistance.hibernate.TemplateIdGatewayImpl;
import pl.gov.coi.cascades.server.persistance.hibernate.mapper.TemplateIdMapper;

import javax.inject.Inject;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 06.04.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@HibernateDevelopmentTest
public class TemplateGatewayImplFunctionalIT {

    private static final String NON_EXISTING_TEMPLATE_ID = "875785887";
    private static final String EXISTING_TEMPLATE_ID = "1";
    private TemplateIdGateway templateIdGateway;
    private String id;
    private String serverId;
    private String version;
    private TemplateIdStatus status;
    private boolean isDefault;

    @Inject
    public void setDatabaseIdGateway(TemplateIdGateway templateIdGateway) {
        this.templateIdGateway = templateIdGateway;
    }

    @Mock
    private TemplateIdMapper templateIdMapper;

    @Mock
    private Logger logger;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        id = "oracle_template";
        serverId = "1234";
        version = "0.0.1";
        status = TemplateIdStatus.CREATED;
        isDefault = true;
    }

    @Test
    public void testSaveWhenLoggerIsInfoEnabled() throws Exception {
        // given
        when(logger.isInfoEnabled()).thenReturn(true);
        TemplateIdGatewayImpl templateIdGatewayImpl = new TemplateIdGatewayImpl(
            templateIdMapper,
            logger
        );
        Template template = Template.builder()
            .id(id)
            .isDefault(isDefault)
            .serverId(serverId)
            .status(status)
            .version(version)
            .build();

        // when
        templateIdGatewayImpl.addTemplate(template);

        // then
        verify(logger).info(contains("20170626:140337"));
        verify(logger).info(contains("Given templateId has been saved."));
    }

    @Test
    public void testSaveWhenLoggerIsNotInfoEnabled() throws Exception {
        // given
        when(logger.isInfoEnabled()).thenReturn(false);
        TemplateIdGatewayImpl templateIdGatewayImpl = new TemplateIdGatewayImpl(
            templateIdMapper,
            logger
        );
        Template template = Template.builder()
            .id(id)
            .isDefault(isDefault)
            .serverId(serverId)
            .status(status)
            .version(version)
            .build();

        // when
        templateIdGatewayImpl.addTemplate(template);

        // then
        verify(logger, times(0)).info(anyString());
    }

    @Test
    public void testFindPositivePath() throws Exception {
        // when
        Optional<Template> actual = templateIdGateway.find(EXISTING_TEMPLATE_ID);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.isPresent()).isTrue();
    }

    @Test
    public void testFindNegativePath() throws Exception {
        // when
        Optional<Template> actual = templateIdGateway.find(NON_EXISTING_TEMPLATE_ID);

        // then
        assertThat(actual).isEmpty();
    }

    @Test
    public void testGetDefaultTemplateIdPositivePath() throws Exception {
        // when
        Optional<Template> actual = templateIdGateway.getDefaultTemplateId();

        // then
        assertThat(actual).isNotNull();
    }

}
