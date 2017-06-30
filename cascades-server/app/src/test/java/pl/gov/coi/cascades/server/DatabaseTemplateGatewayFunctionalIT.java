package pl.gov.coi.cascades.server;

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
import pl.gov.coi.cascades.server.domain.DatabaseTemplateGateway;
import pl.gov.coi.cascades.server.persistance.stub.DatabaseTemplateGatewayStub;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 28.06.17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@HibernateDevelopmentTest
public class DatabaseTemplateGatewayFunctionalIT {

    @Mock
    private Template template;

    @Mock
    private Logger logger;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testDeleteTemplateWhenLoggerIsNotInfoEnabled() {
        // given
        Path path = Paths.get("testPath");
        when(logger.isInfoEnabled()).thenReturn(false);
        DatabaseTemplateGateway gateway = new DatabaseTemplateGatewayStub(
            logger
        );

        // when
        gateway.deleteTemplate(
            template
        );

        // then
        verify(logger, times(0)).info(anyString());
    }

    @Test
    public void testDeleteTemplateWhenLoggerIsInfoEnabled() {
        // given
        Path path = Paths.get("testPath");
        when(logger.isInfoEnabled()).thenReturn(true);
        DatabaseTemplateGateway gateway = new DatabaseTemplateGatewayStub(
            logger
        );

        // when
        gateway.deleteTemplate(
            template
        );

        // then
        verify(logger).info(contains("20170629:083716"));
        verify(logger).info(contains("Given template has been successfully deleted."));
    }


    @Test
    public void testLoadTemplateWhenLoggerIsInfoEnabled() {
        // given
        Path path = Paths.get("testPath");
        when(logger.isInfoEnabled()).thenReturn(true);
        DatabaseTemplateGateway gateway = new DatabaseTemplateGatewayStub(
            logger
        );

        // when
        gateway.createTemplate(
            template,
            path
        );

        // then
        verify(logger).info(contains("20170628:135108"));
        verify(logger).info(contains("Script from " + path + " has been created."));
    }

    @Test
    public void testLoadTemplateWhenLoggerIsNotInfoEnabled() {
        // given
        Path path = Paths.get("testPath");
        when(logger.isInfoEnabled()).thenReturn(false);
        DatabaseTemplateGateway gateway = new DatabaseTemplateGatewayStub(
            logger
        );

        // when
        gateway.createTemplate(
            template,
            path
        );

        // then
        verify(logger, times(0)).info(anyString());
    }

}
