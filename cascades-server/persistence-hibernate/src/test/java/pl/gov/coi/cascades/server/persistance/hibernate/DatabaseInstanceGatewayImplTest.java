package pl.gov.coi.cascades.server.persistance.hibernate;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
public class DatabaseInstanceGatewayImplTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private DatabaseInstanceGatewayImpl databaseInstanceGateway = new DatabaseInstanceGatewayImpl();

    @Test(expected = UnsupportedOperationException.class)
    public void testLaunchDatabase() {
        //when
        databaseInstanceGateway.launchDatabase(getDatabaseInstance());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetRemoteServerId() {
        //when
        databaseInstanceGateway.getRemoteServerId();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testDeleteDatabase() {
        //when
        databaseInstanceGateway.deleteDatabase(getDatabaseInstance());
    }

    private DatabaseInstance getDatabaseInstance() {
        return DatabaseInstance.builder().build();
    }
}
