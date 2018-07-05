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
import pl.gov.coi.cascades.contract.domain.TemplateIdStatus;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.UserGateway;
import pl.gov.coi.cascades.server.persistance.hibernate.TemplateIdGatewayImpl;
import pl.gov.coi.cascades.server.persistance.hibernate.mapper.TemplateIdMapper;

import javax.inject.Inject;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

    private static final String ID = "1";
    private static final String SERVER_ID = "1234";
    private static final String VERSION = "0.0.1";
    private static final TemplateIdStatus STATUS = TemplateIdStatus.CREATED;
    private static final String NAME = "newDatabase";
    private static final boolean IS_DEFAULT = true;

    private TemplateIdGateway templateIdGateway;

    @Inject
    public void setTemplateIdGateway(TemplateIdGateway templateIdGateway) {
        this.templateIdGateway = templateIdGateway;
    }

    @Test
    public void shouldExecutePersistNewTemplate() {
        //given
        pl.gov.coi.cascades.contract.domain.Template template = createTemplate();

        //when
        templateIdGateway.addTemplate(template);
        Optional<pl.gov.coi.cascades.contract.domain.Template> result = templateIdGateway.find(template.getId());

        //then
        assertNotNull(result);
        pl.gov.coi.cascades.contract.domain.Template resultTemplate = result.orElse(null);

        assertNotNull(resultTemplate);
        assertEquals("1", resultTemplate.getId());
        assertEquals("newDatabase", resultTemplate.getName());
        assertEquals("1234", resultTemplate.getServerId());
        assertEquals("0.0.1", resultTemplate.getVersion());
        assertEquals(TemplateIdStatus.CREATED, resultTemplate.getStatus());
    }

    private pl.gov.coi.cascades.contract.domain.Template createTemplate() {
        return pl.gov.coi.cascades.contract.domain.Template.builder()
            .id(ID)
            .name(NAME)
            .isDefault(IS_DEFAULT)
            .serverId(SERVER_ID)
            .status(STATUS)
            .version(VERSION)
            .build();
    }
}
