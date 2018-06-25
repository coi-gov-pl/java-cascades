package pl.gov.coi.cascades.server.persistance.hibernate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseStatus;
import pl.gov.coi.cascades.server.persistance.stub.DatabaseTypeStub;
import pl.gov.coi.cascades.server.persistance.stub.NetworkBindStub;
import pl.gov.coi.cascades.server.persistance.stub.TemplateIdGatewayStub;
import pl.gov.coi.cascades.server.persistance.stub.UsernameAndPasswordCredentialsStub;

import java.time.Instant;
import java.util.Date;

import static junit.framework.TestCase.assertNull;

/**
 * @author Łukasz Małek <lukasz.malek@coi.gov.pl>
 */
@RunWith(MockitoJUnitRunner.class)
public class DatabaseInstanceGatewayImplTest {

    @InjectMocks
    private DatabaseInstanceGatewayImpl databaseInstanceGateway;

    private static final DatabaseId DATABASE_ID1 = new DatabaseId("19");
    private static final DatabaseType DATABASE_TYPE = new DatabaseTypeStub();
    private static final UsernameAndPasswordCredentials USERNAME_AND_PASSWORD_CREDENTIALS1 =
        new UsernameAndPasswordCredentialsStub("Ben Affleck");
    private static final NetworkBind NETWORK_BIND = new NetworkBindStub(5432, "db01.lab.internal");

    @Test
    public void shouldLaunchDatabase() {
        //when
        DatabaseInstance result = databaseInstanceGateway.launchDatabase(getDatabaseInstance());

        //then
        assertNull(result);
    }

    @Test
    public void shouldGetRemoteServerId() {
        //when
        String result = databaseInstanceGateway.getRemoteServerId();

        //then
        assertNull(result);
    }

    private DatabaseInstance getDatabaseInstance() {
        return new DatabaseInstance(
            DATABASE_ID1,
            TemplateIdGatewayStub.TEMPLATE_ID1,
            DATABASE_TYPE,
            "oracle 12c",
            1,
            "ora12e34",
            USERNAME_AND_PASSWORD_CREDENTIALS1,
            NETWORK_BIND,
            DatabaseStatus.LAUNCHED,
            Date.from(Instant.now())
        );
    }
}
