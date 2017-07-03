package pl.gov.coi.cascades.server.persistance.hibernate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.slf4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 03.07.17
 */
public class DatabaseTemplateGatewayImplTest {

    @Mock
    private Logger logger;

    @Mock
    private pl.gov.coi.cascades.contract.domain.Template template;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testDefaultConstructor() {
        // when
        DatabaseTemplateGatewayImpl templateGateway = new DatabaseTemplateGatewayImpl();

        // then
        assertThat(templateGateway).isNotNull();
    }

    @Test
    public void testCreateTemplate() throws Exception {
        // given
        when(logger.isInfoEnabled()).thenReturn(true);
        DatabaseTemplateGatewayImpl templateGateway = new DatabaseTemplateGatewayImpl(
            logger
        );

        // when
        templateGateway.createTemplate(
            template,
            folder.getRoot().toPath()
        );

        // then
        verify(logger).info(contains("20170628:133136"));
        verify(logger).info(contains("Script from " +
            folder.getRoot().toPath() +
            " has been saved successfully in the database.")
        );
    }

    @Test
    public void testDeleteTemplate() throws Exception {
        // given
        when(logger.isInfoEnabled()).thenReturn(true);
        DatabaseTemplateGatewayImpl templateGateway = new DatabaseTemplateGatewayImpl(
            logger
        );

        // when
        templateGateway.deleteTemplate(
            template
        );

        // then
        verify(logger).info(contains("20170629:082315"));
        verify(logger).info(contains("Given template has been successfully deleted from database."));
    }

}
