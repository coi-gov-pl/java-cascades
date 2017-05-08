package pl.gov.coi.cascades.server.persistance.hibernate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseTypeClassNameService;
import pl.gov.coi.cascades.server.domain.DatabaseTypeDTO;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.Credentials;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.NetworkBind;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateId;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateIdStatus;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 08.05.17.
 */
public class DatabaseIdGatewayImplTest {

    @Mock
    private DatabaseTypeClassNameService databaseTypeClassNameService;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Object> query;

    @Mock
    private DatabaseTypeDTO databaseTypeDTO;

    @Mock
    private pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateId hibernateTemplate;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testFind() throws Exception {
        // given
        String id = "123456789";
        String PASSWORD = "12345678";
        String USERNAME = "Ben Affleck";
        String HOST = "db01.lab.internal";
        int PORT = 5432;
        Long DATABASE_ID_AS_LONG = 45L;
        String DATABASE_TYPE = "stub";
        String INSTANCE_NAME = "ora12e34";
        String DATABASE_NAME = "oracle 12c";
        String SERVER_ID = "5v36y5646";
        long TEMPLATE_ID = 8958395489L;
        long ID = -2174612783412L;
        String TEMPLATE_ID_NAME = "oracle_template";
        Date created = Date.from(Instant.now());
        DatabaseId databaseId = new DatabaseId(
            id
        );
        when(databaseTypeClassNameService.getDatabaseType(anyString())).thenReturn(databaseTypeDTO);
        when(databaseTypeDTO.onFail(any())).thenReturn(databaseTypeDTO);
        when(databaseTypeDTO.onSuccess(any())).thenReturn(databaseTypeDTO);
        doNothing().when(databaseTypeDTO).resolve();
        DatabaseIdGatewayImpl databaseIdGatewayImpl = new DatabaseIdGatewayImpl(
            databaseTypeClassNameService
        );
        Credentials credentials = new Credentials();
        credentials.setPassword(PASSWORD);
        credentials.setUsername(USERNAME);
        NetworkBind networkBind = new NetworkBind();
        networkBind.setHost(HOST);
        networkBind.setPort(PORT);
        pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance hibernateInstance
            = new pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance();
        hibernateInstance.setId(DATABASE_ID_AS_LONG);
        TemplateId templateId = new TemplateId();
        templateId.setDefault(false);
        templateId.setName(TEMPLATE_ID_NAME);
        templateId.setServerId(SERVER_ID);
        templateId.setId(TEMPLATE_ID);
        templateId.setStatus(TemplateIdStatus.CREATED);
        hibernateInstance.setTemplateId(templateId);
        hibernateInstance.setType(DATABASE_TYPE);
        hibernateInstance.setInstanceName(INSTANCE_NAME);
        hibernateInstance.setReuseTimes(1);
        hibernateInstance.setDatabaseName(DATABASE_NAME);
        hibernateInstance.setCredentials(credentials);
        hibernateInstance.setNetworkBind(networkBind);
        hibernateInstance.setStatus(pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseStatus.DELETED);
        hibernateInstance.setCreated(created);
        hibernateInstance.setId(ID);
        databaseIdGatewayImpl.setEntityManager(entityManager);
        when(entityManager.createQuery(anyString(), any())).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.setMaxResults(anyInt())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(hibernateInstance);

        // when
        Optional<DatabaseInstance> actual = databaseIdGatewayImpl.findInstance(databaseId);

        // then
        assertThat(actual.isPresent()).isTrue();
    }

    @Test
    public void testFindWhenExceptionOccurred() throws Exception {
        // given
        String id = "123456789";
        DatabaseId databaseId = new DatabaseId(
            id
        );
        NoResultException exception = new NoResultException("There is no result.");

        DatabaseIdGatewayImpl databaseIdGatewayImpl = new DatabaseIdGatewayImpl(
            databaseTypeClassNameService
        );
        databaseIdGatewayImpl.setEntityManager(entityManager);
        when(entityManager.createQuery(anyString(), any())).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.setMaxResults(anyInt())).thenReturn(query);
        when(query.getSingleResult()).thenThrow(exception);

        // then
        expectedException.expectMessage(contains("20170402:222713"));

        // when
        databaseIdGatewayImpl.findInstance(databaseId);
    }

}
