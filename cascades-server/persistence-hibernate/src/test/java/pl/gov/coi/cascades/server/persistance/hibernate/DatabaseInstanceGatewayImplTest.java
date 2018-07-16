package pl.gov.coi.cascades.server.persistance.hibernate;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.persistance.hibernate.mapper.DatabaseInstanceMapper;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
public class DatabaseInstanceGatewayImplTest {

    @Mock
    private DatabaseInstanceMapper databaseInstanceMapper;

    @Mock
    private EntityManager entityManager;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private DatabaseInstanceGatewayImpl databaseInstanceGateway;

    @Before
    public void init() {
        databaseInstanceGateway = new DatabaseInstanceGatewayImpl(
            databaseInstanceMapper
        );
        databaseInstanceGateway.setEntityManager(entityManager);
    }

    @Test
    public void shouldSaveDatabaseInstance() {
        //given
        pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance databaseInstanceEntity
            = new pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance();

        given(databaseInstanceMapper.toHibernateEntity(any(DatabaseInstance.class))).willReturn(databaseInstanceEntity);

        //when
        DatabaseInstance result = databaseInstanceGateway.save(getDatabaseInstance());

        //then
        verify(entityManager).merge(eq(databaseInstanceEntity));
        assertNotNull(result);
    }

    @Test
    public void shouldDeleteDatabase () {
        //given
        pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance databaseInstanceEntity
            = new pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance();

        given(databaseInstanceMapper.toHibernateEntity(any(DatabaseInstance.class))).willReturn(databaseInstanceEntity);

        //when
        databaseInstanceGateway.deleteDatabase(getDatabaseInstance());

        //then
        verify(entityManager).merge(eq(databaseInstanceEntity));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldGetRemoteServerId() {
        //when
        databaseInstanceGateway.getRemoteServerId();
    }

    private DatabaseInstance getDatabaseInstance() {
        return DatabaseInstance.builder().build();
    }
}
