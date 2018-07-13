package pl.gov.coi.cascades.server;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 04.08.17
 */
public class ConnectionConfiguratorTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private ConnectionConfigurator connectionConfigurator = new ConnectionConfigurator();

    @Test
    public void testGetConnectionConfigurationForNotSupportedType() {
        // given
        String notSupportedType = "mysql";

        // then
        expectedException.expect(EidIllegalArgumentException.class);
        expectedException.expectMessage("20170728:150904");
        expectedException.expectMessage("Given database type 'mysql' hasn't been recognised.");

        // when
        connectionConfigurator.getConnectionConfiguration(notSupportedType);
    }

    @Test
    public void testGetConnectionConfigurationForOracle() {
        // given
        String oracleType = "ora12c";

        // when
        ConnectionConfiguration actual = connectionConfigurator.getConnectionConfiguration(oracleType);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getDriverClass()).isEqualTo("oracle.jdbc.OracleDriver");
        assertThat(actual.getJdbcUrlTemplate()).isEqualTo("jdbc:oracle:thin:@//%s:%d/%s");
    }

    @Test
    public void testGetConnectionConfigurationForPostgreSQL() {
        // given
        String postgreSQLType = "pgsql";

        // when
        ConnectionConfiguration actual = connectionConfigurator.getConnectionConfiguration(postgreSQLType);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getDriverClass()).isEqualTo("org.postgresql.Driver");
        assertThat(actual.getJdbcUrlTemplate()).isEqualTo("jdbc:postgresql://%s:%d/%s");
    }

}
