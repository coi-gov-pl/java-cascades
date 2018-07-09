package pl.gov.coi.cascades.server.persistance.stub;

import org.junit.Test;
import org.mockito.Mock;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.server.domain.DatabaseIdGateway;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseLimitGateway;
import pl.gov.coi.cascades.server.domain.DatabaseOperations;
import pl.gov.coi.cascades.server.domain.DatabaseTemplateGateway;
import pl.gov.coi.cascades.server.domain.DatabaseUserGateway;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.UserGateway;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.04.17.
 */
public class PersistanceStubByDefaultOnDevelopmentAutoConfigurationTest {

    @Test
    public void testProduceDatabaseTemplateGateway() throws Exception {
        // given
        PersistanceStubByDefaultOnDevelopmentAutoConfiguration stubs = new PersistanceStubByDefaultOnDevelopmentAutoConfiguration();

        // when
        DatabaseTemplateGateway actual = stubs.produceDatabaseTemplateGateway();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testProduceStubDatabase() throws Exception {
        // given
        PersistanceStubByDefaultOnDevelopmentAutoConfiguration stubs = new PersistanceStubByDefaultOnDevelopmentAutoConfiguration();

        // when
        Map<Object, User> actual = stubs.produceStubDatabase();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testProduceTemplateIdGateway() throws Exception {
        // given
        PersistanceStubByDefaultOnDevelopmentAutoConfiguration stubs = new PersistanceStubByDefaultOnDevelopmentAutoConfiguration();

        // when
        TemplateIdGateway actual = stubs.produceTemplateIdGateway();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testProduceDatabaseInstanceGateway() throws Exception {
        // given
        PersistanceStubByDefaultOnDevelopmentAutoConfiguration stubs = new PersistanceStubByDefaultOnDevelopmentAutoConfiguration();

        // when
        DatabaseOperations actual = stubs.produceDatabaseOperations();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testProduceUserGateway() throws Exception {
        // given
        PersistanceStubByDefaultOnDevelopmentAutoConfiguration stubs = new PersistanceStubByDefaultOnDevelopmentAutoConfiguration();

        // when
        UserGateway actual = stubs.produceUserGateway(
            stubs.produceStubDatabase()
        );

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testProduceDatabaseLimitGateway() throws Exception {
        // given
        PersistanceStubByDefaultOnDevelopmentAutoConfiguration stubs = new PersistanceStubByDefaultOnDevelopmentAutoConfiguration();

        // when
        DatabaseLimitGateway actual = stubs.produceDatabaseLimitGateway();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testProduceDatabaseIdGateway() throws Exception {
        // given
        PersistanceStubByDefaultOnDevelopmentAutoConfiguration stubs = new PersistanceStubByDefaultOnDevelopmentAutoConfiguration();

        // when
        DatabaseIdGateway actual = stubs.produceDatabaseIdGateway();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testProduceDatabaseType() throws Exception {
        // given
        PersistanceStubByDefaultOnDevelopmentAutoConfiguration stubs = new PersistanceStubByDefaultOnDevelopmentAutoConfiguration();

        // when
        DatabaseType actual = stubs.produceDatabaseType();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testProduceDatabaseOperations() throws Exception {
        // given
        PersistanceStubByDefaultOnDevelopmentAutoConfiguration stubs = new PersistanceStubByDefaultOnDevelopmentAutoConfiguration();

        // when
        DatabaseOperations actual = stubs.produceDatabaseOperations();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testProduceDatabaseUserGateway() throws Exception {
        // given
        PersistanceStubByDefaultOnDevelopmentAutoConfiguration stubs = new PersistanceStubByDefaultOnDevelopmentAutoConfiguration();

        // when
        DatabaseUserGateway actual = stubs.produceDatabaseUserGateway();

        // then
        assertThat(actual).isNotNull();
    }
}
