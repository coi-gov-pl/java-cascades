package pl.gov.coi.cascades.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.gov.coi.cascades.contract.domain.TemplateIdStatus;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.Template;
import pl.gov.coi.cascades.server.persistance.hibernate.mapper.TemplateIdMapper;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
    private static final String TEMPLATE_ID_FIELD = "generatedId";

    private TemplateIdGateway templateIdGateway;
    private EntityManager entityManager;
    private TemplateIdMapper templateIdMapper = new TemplateIdMapper();

    @Inject
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

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
        Optional<pl.gov.coi.cascades.contract.domain.Template> result = findAddTemplateById(template.getId());

        //then
        assertNotNull(result);
        pl.gov.coi.cascades.contract.domain.Template resultTemplate = result.orElse(null);

        assertNotNull(resultTemplate);
        assertEquals(template.getId(), resultTemplate.getId());
        assertEquals(template.getName(), resultTemplate.getName());
        assertEquals(template.getServerId(), resultTemplate.getServerId());
        assertEquals(template.getVersion(), resultTemplate.getVersion());
        assertEquals(template.getStatus(), resultTemplate.getStatus());
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

    private Optional<pl.gov.coi.cascades.contract.domain.Template> findAddTemplateById(String generatedId) {
        TypedQuery<Template> query =
            entityManager.createQuery(
                "SELECT template FROM Template template " +
                    "WHERE template.generatedId = :generatedId", Template.class
            )
                .setParameter(TEMPLATE_ID_FIELD, generatedId)
                .setMaxResults(1);

        return Optional.of(templateIdMapper.fromHibernateEntity(query.getSingleResult()));
    }
}
