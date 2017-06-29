package pl.gov.coi.cascades.server.persistance.hibernate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.slf4j.Logger;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateIdStatus;
import pl.gov.coi.cascades.server.persistance.hibernate.mapper.TemplateIdMapper;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.*;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 08.05.17.
 */
public class TemplateGatewayImplTest {

    @Mock
    private TemplateIdMapper templateIdMapper;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Object> query;

    @Mock
    private Logger logger;

    @Mock
    private pl.gov.coi.cascades.server.persistance.hibernate.entity.Template hibernateTemplate;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testGetDefaultTemplateIdWhenExceptionOccurred() throws Exception {
        // given
        NoResultException exception = new NoResultException("There is no result.");
        TemplateIdGatewayImpl templateIdGatewayImpl = new TemplateIdGatewayImpl(
            templateIdMapper,
            logger
        );
        templateIdGatewayImpl.setEntityManager(entityManager);
        when(entityManager.createQuery(anyString(), any())).thenThrow(exception);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.setMaxResults(anyInt())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(hibernateTemplate);

        // when
        Optional<Template> actual = templateIdGatewayImpl.getDefaultTemplateId();

        // then
        assertThat(actual.isPresent()).isFalse();
        verify(logger, times(1)).error(contains("20170406:092655"));
    }

    @Test
    public void testGetDefaultTemplateId() throws Exception {
        // given
        String name = "123456789";
        TemplateIdStatus status = TemplateIdStatus.CREATED;
        String serverId = "hufuiht8t757";
        String generatedId = "hfb6n2jg";
        String version = "0.0.1";
        pl.gov.coi.cascades.server.persistance.hibernate.entity.Template template =
            new pl.gov.coi.cascades.server.persistance.hibernate.entity.Template();
        template.setGeneratedId(generatedId);
        template.setServerId(serverId);
        template.setStatus(status);
        template.setName(name);
        template.setVersion(version);
        template.setDefault(false);

        TemplateIdGatewayImpl templateIdGatewayImpl = new TemplateIdGatewayImpl();
        templateIdGatewayImpl.setEntityManager(entityManager);
        when(entityManager.createQuery(anyString(), any())).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.setMaxResults(anyInt())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(template);

        // when
        Optional<Template> actual = templateIdGatewayImpl.getDefaultTemplateId();

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get().getName()).isEqualTo(name);
        assertThat(actual.get().getServerId()).isEqualTo(serverId);
        assertThat(actual.get().getStatus().name()).isEqualTo(status.name());
        assertThat(actual.get().isDefault()).isFalse();
    }

    @Test
    public void testFind() throws Exception {
        // given
        String name = "123456789";
        String id = "gs46h77f";
        String version = "0.0.1";
        pl.gov.coi.cascades.contract.domain.TemplateIdStatus status = pl.gov.coi.cascades.contract.domain.TemplateIdStatus.CREATED;
        String serverId = "hufuiht8t757";
        Template template = new Template(
            id,
            name,
            status,
            false,
            serverId,
            version
        );

        TemplateIdGatewayImpl templateIdGatewayImpl = new TemplateIdGatewayImpl(
            templateIdMapper,
            logger
        );
        templateIdGatewayImpl.setEntityManager(entityManager);
        when(entityManager.createQuery(anyString(), any())).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.setMaxResults(anyInt())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(hibernateTemplate);
        when(templateIdMapper.fromHibernateEntity(any())).thenReturn(template);

        // when
        Optional<Template> actual = templateIdGatewayImpl.find(name);

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get().getId()).isEqualTo(id);
        assertThat(actual.get().getServerId()).isEqualTo(serverId);
        assertThat(actual.get().getStatus()).isEqualTo(status);
        assertThat(actual.get().isDefault()).isFalse();
    }

    @Test
    public void testFindWhenExceptionOccurred() throws Exception {
        // given
        String templateId = "12345678";
        NoResultException exception = new NoResultException("There is no result.");

        TemplateIdGatewayImpl templateIdGatewayImpl = new TemplateIdGatewayImpl(
            templateIdMapper,
            logger
        );
        templateIdGatewayImpl.setEntityManager(entityManager);
        when(entityManager.createQuery(anyString(), any())).thenThrow(exception);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.setMaxResults(anyInt())).thenReturn(query);

        // when
        Optional<Template> actual = templateIdGatewayImpl.find(templateId);

        // then
        assertThat(actual.isPresent()).isFalse();
        verify(logger, times(1)).error(contains("20170330:092228"));
    }

}
