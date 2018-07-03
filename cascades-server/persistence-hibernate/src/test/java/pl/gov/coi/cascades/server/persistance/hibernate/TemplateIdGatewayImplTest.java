package pl.gov.coi.cascades.server.persistance.hibernate;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.slf4j.Logger;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.server.persistance.hibernate.mapper.TemplateIdMapper;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
public class TemplateIdGatewayImplTest {

    private static final String TEMPLATE_ID = "2137";
    private static final String THERE_IS_NO_RESULT = "There is no result.";

    private TemplateIdGatewayImpl templateIdGatewayImpl;

    @Mock
    private TemplateIdMapper templateIdMapper;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Object> query;

    @Mock
    private Logger logger;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void init() {
        templateIdGatewayImpl = new TemplateIdGatewayImpl(
            templateIdMapper,
            logger
        );

        templateIdGatewayImpl.setEntityManager(entityManager);
    }

    @Test
    public void testSaveWhenLoggerIsInfoEnabled() {
        // given
        Template template = createTemplate();

        given(templateIdMapper
            .toHibernateEntity(any(Template.class)))
            .willReturn(new pl.gov.coi.cascades.server.persistance.hibernate.entity.Template());
        given(logger.isInfoEnabled()).willReturn(true);

        // when
        templateIdGatewayImpl.addTemplate(template);

        // then
        verify(logger).info(contains("20170626:140337"));
        verify(logger).info(contains("Given templateId has been saved."));
    }

    @Test
    public void testSaveWhenLoggerIsNotInfoEnabled() throws Exception {
        // given
        Template template = createTemplate();

        // when
        templateIdGatewayImpl.addTemplate(template);

        // then
        verify(logger, times(0)).info(anyString());
    }

    @Test
    public void testFindPositivePath() throws Exception {
        //given
        given(entityManager.createQuery(anyString(), any())).willReturn(query);
        given(query.setParameter(anyString(), anyString())).willReturn(query);
        given(query.setMaxResults(anyInt())).willReturn(query);
        given(query.getSingleResult()).willReturn(new pl.gov.coi.cascades.server.persistance.hibernate.entity.Template());

        given(templateIdMapper
            .fromHibernateEntity(any(pl.gov.coi.cascades.server.persistance.hibernate.entity.Template.class)))
            .willReturn(createTemplate());

        // when
        Optional<Template> actual = templateIdGatewayImpl.find(TEMPLATE_ID);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.isPresent()).isTrue();
    }

    @Test
    public void testFindNegativePath() throws Exception {
        //given
        NoResultException exception = new NoResultException(THERE_IS_NO_RESULT);

        given(entityManager.createQuery(anyString(), any())).willThrow(exception);
        given(query.setParameter(anyString(), anyString())).willReturn(query);
        given(query.setMaxResults(anyInt())).willReturn(query);

        //when
        Optional<Template> result = templateIdGatewayImpl.find(TEMPLATE_ID);

        // then
        assertFalse(result.isPresent());
        verify(logger, times(1)).error(contains("20170330:092228"));
    }

    @Test
    public void testGetDefaultTemplateIdPositivePath() throws Exception {
        //given
        given(entityManager.createQuery(anyString(), any())).willReturn(query);
        given(query.setParameter(anyString(), anyString())).willReturn(query);
        given(query.setMaxResults(anyInt())).willReturn(query);
        given(query.getSingleResult()).willReturn(new pl.gov.coi.cascades.server.persistance.hibernate.entity.Template());

        given(templateIdMapper
            .fromHibernateEntity(any(pl.gov.coi.cascades.server.persistance.hibernate.entity.Template.class)))
            .willReturn(createTemplate());

        // when
        Optional<Template> actual = templateIdGatewayImpl.getDefaultTemplateId();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void shouldExecutePersistNewTemplate() {
        //given
        pl.gov.coi.cascades.server.persistance.hibernate.entity.Template templateEntity =
            new pl.gov.coi.cascades.server.persistance.hibernate.entity.Template();

        given(templateIdMapper.toHibernateEntity(any(Template.class))).willReturn(templateEntity);

        //when
        templateIdGatewayImpl.addTemplate(createTemplate());

        //then
        verify(entityManager).persist(eq(templateEntity));
        verify(entityManager, times(1))
            .persist(any(pl.gov.coi.cascades.server.persistance.hibernate.entity.Template.class));
    }

    private Template createTemplate() {
        return Template.builder().build();
    }
}
