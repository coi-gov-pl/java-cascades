package pl.gov.coi.cascades.server.domain;

import lombok.Getter;
import lombok.Setter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 08.03.17.
 */
public class DatabaseInstanceTest {

    @Mock
    private DatabaseId databaseId;

    @Mock
    private TemplateId templateId;

    @Mock
    private DatabaseType databaseType;

    @Mock
    private UsernameAndPasswordCredentials credentials;

    @Getter
    private NetworkBind networkBind;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testGetCreated() throws Exception {
        // given
        String instanceName = "PESEL";
        String databaseName = "orae231r";
        String status = "active";
        Date created = Date.from(Instant.now());
        DatabaseInstance databaseInstance = new DatabaseInstance(
            databaseId,
            templateId,
            databaseType,
            instanceName,
            0,
            databaseName,
            credentials,
            networkBind,
            status,
            created
        );

        // when
        Date actual = databaseInstance.getCreated();

        // then
        assertThat(actual).isEqualTo(created);
    }

    @Test
    public void testSetNetworkBind() throws Exception {
        // given
        String instanceName = "PESEL";
        String databaseName = "orae231r";
        String status = "active";
        Date created = Date.from(Instant.now());
        NetworkBindImplTest networkBindImplTest = new NetworkBindImplTest();
        networkBindImplTest.setHost("localhost");
        networkBindImplTest.setPort(8080);
        DatabaseInstance databaseInstance = new DatabaseInstance(
            databaseId,
            templateId,
            databaseType,
            instanceName,
            0,
            databaseName,
            credentials,
            null,
            status,
            created
        );

        // when
        DatabaseInstance actual = databaseInstance.setNetworkBind(networkBindImplTest);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getNetworkBind()).isNotNull();
        assertThat(actual.getNetworkBind().getHost()).isEqualTo("localhost");
        assertThat(actual.getNetworkBind().getPort()).isEqualTo(8080);
        assertThat(databaseInstance.getNetworkBind()).isNull();
        assertThat(actual.getNetworkBind()).isNotSameAs(databaseInstance);
    }

    private class NetworkBindImplTest implements NetworkBind {

        @Getter
        @Setter
        private int port;
        @Getter
        @Setter
        private String host;

    }
}
