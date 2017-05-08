package pl.gov.coi.cascades.server.persistance.hibernate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.slf4j.Logger;
import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.contract.domain.TemplateIdStatus;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 08.05.17.
 */
public class TemplateIdGatewayImplTest {

    @Mock
    private TemplateIdMapper templateIdMapper;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Object> query;

    @Mock
    private Logger logger;

    @Mock
    private pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateId hibernateTemplate;

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
        Optional<TemplateId> actual = templateIdGatewayImpl.getDefaultTemplateId();

        // then
        assertThat(actual.isPresent()).isFalse();
        verify(logger, times(1)).error(contains("20170406:092655"));
    }

    @Test
    public void testGetDefaultTemplateId() throws Exception {
        // given
        String name = "123456789";
        String id = "123456789";
        TemplateIdStatus status = TemplateIdStatus.CREATED;
        String serverId = "hufuiht8t757";
        TemplateId templateId = new TemplateId(
            id,
            status,
            true,
            serverId
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
        when(templateIdMapper.fromHibernateEntity(any())).thenReturn(templateId);

        // when
        Optional<TemplateId> actual = templateIdGatewayImpl.getDefaultTemplateId();

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get().getId()).isEqualTo(id);
        assertThat(actual.get().getServerId()).isEqualTo(serverId);
        assertThat(actual.get().getStatus()).isEqualTo(status);
        assertThat(actual.get().isDefault()).isTrue();
    }

    @Test
    public void testFind() throws Exception {
        // given
        String name = "123456789";
        String id = "123456789";
        TemplateIdStatus status = TemplateIdStatus.CREATED;
        String serverId = "hufuiht8t757";
        TemplateId templateId = new TemplateId(
            id,
            status,
            false,
            serverId
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
        when(templateIdMapper.fromHibernateEntity(any())).thenReturn(templateId);

        // when
        Optional<TemplateId> actual = templateIdGatewayImpl.find(name);

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
        Optional<TemplateId> actual = templateIdGatewayImpl.find(templateId);

        // then
        assertThat(actual.isPresent()).isFalse();
        verify(logger, times(1)).error(contains("20170330:092228"));
    }

}
