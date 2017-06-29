package pl.gov.coi.cascades.contract.service;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.contract.domain.Template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 21.02.17.
 */
public class RemoteDatabaseRequestTest {

    private RemoteDatabaseRequest remoteDatabaseRequest;
    private String instanceName;
    private String typeClassName;

    @Mock
    private Template template;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        instanceName = "PESEL";
        typeClassName = "typeClassName";
        remoteDatabaseRequest = new RemoteDatabaseRequest(
            typeClassName,
            template,
            instanceName
        );
    }

    @Test
    public void testDefaultConstructor() throws Exception {
        // when
        RemoteDatabaseRequest actual = new RemoteDatabaseRequest(
            typeClassName,
            template,
            instanceName
        );

        // then
        assertNotNull(actual);
    }

    @Test
    public void getInstanceName() throws Exception {
        // when
        Optional<String> actual = remoteDatabaseRequest.getInstanceName();

        // then
        assertEquals(Optional.fromNullable(instanceName), actual);
    }

}
