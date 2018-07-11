package pl.gov.coi.cascades.server;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.07.17
 */
public class DatabaseEndpointManagerTest {

    @Mock
    private DriverManagerDataSource driverManagerDataSource;

    @Mock
    private DriverManagerDataSourceHelper driverManagerDataSourceHelper;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private DatabaseEndpointManager databaseEndpointManager;

    @Before
    public void setup() {
        Map<String, DriverManagerDataSource> managerDataSourceMap = new HashMap<>();
        managerDataSourceMap.put("test", driverManagerDataSource);
        databaseEndpointManager = new DatabaseEndpointManager(
            managerDataSourceMap,
            driverManagerDataSourceHelper
        );
        when(driverManagerDataSource.getUrl()).thenReturn("jdbc://database:123");
    }

    @Test
    public void testGetWhenErrorOccurred() {
        // then
        expectedException.expect(EidIllegalArgumentException.class);
        expectedException.expectMessage("20170726:121616");
        expectedException.expectMessage("Given serverId hasn't been found.");

        // when
        databaseEndpointManager.getConnectionToServer("not_existing_key");
    }

    @Test
    public void testGetConnectionToServer() {
        // when
        ConnectionDatabase actual = databaseEndpointManager.getConnectionToServer("test");

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getType()).isEqualTo("jdbc://database:123");
        assertThat(actual.getJdbcTemplate().getDataSource()).isEqualTo(driverManagerDataSource);
    }

    @Test
    public void testGetConnectionToTemplate() {
        // given
        when(driverManagerDataSourceHelper.getManager("serverId", "templateName")).thenReturn(driverManagerDataSource);

        // when
        ConnectionDatabase actual = databaseEndpointManager.getConnectionToDatabase("serverId", "templateName");

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getType()).isEqualTo("jdbc://database:123");
        assertThat(actual.getJdbcTemplate().getDataSource()).isEqualTo(driverManagerDataSource);
    }

}
