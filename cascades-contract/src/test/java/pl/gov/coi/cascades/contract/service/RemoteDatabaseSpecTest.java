package pl.gov.coi.cascades.contract.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.ConnectionStringProducer;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 21.02.17.
 */
public class RemoteDatabaseSpecTest {
    private RemoteDatabaseSpec remoteDatabaseSpec;
    private String databaseName;

    @Mock
    private DatabaseId databaseId;

    @Mock
    private DatabaseType databaseType;

    @Mock
    private NetworkBind networkBind;

    @Mock
    private UsernameAndPasswordCredentials credentials;

    @Mock
    private ConnectionStringProducer connectionStringProducer;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        databaseName = "Oracle";
    }

    @Test
    public void testDefaultConstructor() {
        // when
        RemoteDatabaseSpec actual = new RemoteDatabaseSpec(
            databaseType,
            databaseId,
            databaseName,
            networkBind,
            credentials
        );

        // then
        assertNotNull(actual);
    }

    @Test
    public void testGetConnectionString() {
        // given
        RemoteDatabaseSpec spec = new RemoteDatabaseSpec(
            databaseType,
            databaseId,
            databaseName,
            networkBind,
            credentials
        );
        when(databaseType.getConnectionStringProducer())
            .thenReturn(connectionStringProducer);
        when(connectionStringProducer.produce(any(NetworkBind.class), anyString()))
            .thenReturn("db-conn");

        // when
        String conn = spec.getConnectionString();

        // then
        assertEquals("db-conn", conn);
    }

}
