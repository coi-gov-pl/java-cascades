package pl.gov.coi.cascades.contract.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.slf4j.Logger;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;

import static org.junit.Assert.assertNotNull;

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
    private Logger logger;

    @Mock
    private NetworkBind networkBind;

    @Mock
    private UsernameAndPasswordCredentials credentials;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        databaseName = "Oracle";
    }

    @Test
    public void testDefaultConstructor() throws Exception {
        // when
        RemoteDatabaseSpec actual = new RemoteDatabaseSpec(
                databaseType,
                databaseId,
                databaseName,
                logger,
                networkBind,
                credentials
        );

        // then
        assertNotNull(actual);
    }

}