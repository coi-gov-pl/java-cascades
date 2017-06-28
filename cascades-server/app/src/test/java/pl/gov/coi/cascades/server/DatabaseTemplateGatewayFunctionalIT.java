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
import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.server.domain.DatabaseTemplateGateway;
import pl.gov.coi.cascades.server.persistance.hibernate.DatabaseTemplateGatewayImpl;

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
    private TemplateId templateId;

    @Mock
    private Logger logger;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testLoadTemplateWhenLoggerIsInfoEnabled() {
        // given
        Path path = Paths.get("testPath");
        when(logger.isInfoEnabled()).thenReturn(true);
        DatabaseTemplateGateway gateway = new DatabaseTemplateGatewayImpl(
            logger
        );

        // when
        gateway.createTemplate(
            templateId,
            path
        );

        // then
        verify(logger).info(contains("20170628:133136"));
        verify(logger).info(contains("Script, loaded in " + path + " has been saved."));
    }

    @Test
    public void testLoadTemplateWhenLoggerIsNotInfoEnabled() {
        // given
        Path path = Paths.get("testPath");
        when(logger.isInfoEnabled()).thenReturn(false);
        DatabaseTemplateGateway gateway = new DatabaseTemplateGatewayImpl(
            logger
        );

        // when
        gateway.createTemplate(
            templateId,
            path
        );

        // then
        verify(logger, times(0)).info(anyString());
    }

}
