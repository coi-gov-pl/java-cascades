package pl.gov.coi.cascades.server;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.07.17
 */
public class DatabaseEndpointManagerTest {

    @Mock
    private DriverManagerDataSource driverManagerDataSource;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testGetWhenErrorOccurred() throws Exception {
        // given
        Map<String, DriverManagerDataSource> managerDataSourceMap = new HashMap<>();
        managerDataSourceMap.put("test", driverManagerDataSource);
        DatabaseEndpointManager databaseEndpointManager = new DatabaseEndpointManager(
            managerDataSourceMap
        );

        // then
        expectedException.expect(EidIllegalArgumentException.class);
        expectedException.expectMessage("20170726:121616");
        expectedException.expectMessage("Given serverId hasn't been found.");

        // when
        databaseEndpointManager.get("not_existing_key");
    }

    @Test
    public void testGet() throws Exception {
        // given
        Map<String, DriverManagerDataSource> managerDataSourceMap = new HashMap<>();
        managerDataSourceMap.put("test", driverManagerDataSource);
        DatabaseEndpointManager databaseEndpointManager = new DatabaseEndpointManager(
            managerDataSourceMap
        );

        // when
        JdbcTemplate actual = databaseEndpointManager.get("test");

        // then
        assertThat(actual).isNotNull();
    }

}
