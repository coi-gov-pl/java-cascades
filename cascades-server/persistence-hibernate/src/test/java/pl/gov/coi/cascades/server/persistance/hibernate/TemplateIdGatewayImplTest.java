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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
public class TemplateIdGatewayImplTest {

    private static final String THERE_IS_NO_RESULT = "There is no result.";
    private static final String EMPTY_STRING = "";

    private TemplateIdGatewayImpl templateIdGateway;

    @Mock
    private TemplateIdMapper templateIdMapper;

    @Mock
    private Logger logger;

    @Mock
    private TypedQuery<Object> query;

    @Mock
    private EntityManager entityManager;

    @Mock
    private pl.gov.coi.cascades.server.persistance.hibernate.entity.Template template;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void init() {
        templateIdGateway = new TemplateIdGatewayImpl(
            templateIdMapper,
            logger
        );

        templateIdGateway.setEntityManager(entityManager);
    }

    @Test
    public void shouldFindTemplate() {
        //given
        given(entityManager.createQuery(anyString(), any())).willReturn(query);
        given(query.setParameter(anyString(), anyString())).willReturn(query);
        given(query.setMaxResults(anyInt())).willReturn(query);
        given(query.getSingleResult()).willReturn(template);

        given(templateIdMapper
            .fromHibernateEntity(any(pl.gov.coi.cascades.server.persistance.hibernate.entity.Template.class)))
            .willReturn(Template.builder().build());

        //when
        Optional<Template> result = templateIdGateway.find(EMPTY_STRING);

        //then
        assertNotNull(result);
    }

    @Test
    public void shouldFindWhenExceptionOccurred() {
        //given
        NoResultException exception = new NoResultException(THERE_IS_NO_RESULT);

        given(entityManager.createQuery(anyString(), any())).willThrow(exception);
        given(query.setParameter(anyString(), anyString())).willReturn(query);
        given(query.setMaxResults(anyInt())).willReturn(query);

        //when
        Optional<Template> result = templateIdGateway.find(EMPTY_STRING);

        // then
        assertFalse(result.isPresent());
        verify(logger, times(1)).error(contains("20170330:092228"));
    }
}
