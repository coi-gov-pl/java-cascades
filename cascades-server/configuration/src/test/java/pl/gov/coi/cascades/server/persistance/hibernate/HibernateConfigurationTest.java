package pl.gov.coi.cascades.server.persistance.hibernate;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.server.domain.DatabaseIdGateway;
import pl.gov.coi.cascades.server.domain.DatabaseInstanceGateway;
import pl.gov.coi.cascades.server.domain.DatabaseLimitGateway;
import pl.gov.coi.cascades.server.domain.DatabaseTypeClassNameService;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;
import pl.gov.coi.cascades.server.domain.UserGateway;
import pl.gov.coi.cascades.server.persistance.hibernate.mapper.DatabaseInstanceMapper;
import pl.gov.coi.cascades.server.persistance.hibernate.mapper.UserMapper;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.04.17.
 */
public class HibernateConfigurationTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private DatabaseInstanceMapper databaseInstanceMapper;

    @Mock
    private DatabaseTypeClassNameService databaseTypeClassNameService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private HibernateConfiguration hibernateConfiguration = new HibernateConfiguration();

    @Test
    public void testCreateTemplateIdGateway() throws Exception {
        // when
        TemplateIdGateway actual = hibernateConfiguration.createTemplateIdGateway();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testCreateUserGateway() throws Exception {
        // when
        UserGateway actual = hibernateConfiguration.createUserGateway(
            userMapper
        );

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testCreateDatabaseIdGateway() throws Exception {
        // when
        DatabaseIdGateway actual = hibernateConfiguration.createDatabaseIdGateway(
            databaseInstanceMapper
        );

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testDatabaseInstanceGateway() throws Exception {
        // when
        DatabaseInstanceGateway actual = hibernateConfiguration.createDatabaseInstanceGateway(
            databaseInstanceMapper
        );

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testDatabaseLimitGateway() throws Exception {
        // when
        DatabaseLimitGateway actual = hibernateConfiguration.createDatabaseLimitGateway(
            databaseInstanceMapper
        );

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testUserMapper() throws Exception {
        // when
        UserMapper actual = hibernateConfiguration.createUserMapper(
            databaseTypeClassNameService
        );

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testDatabaseInstanceMapper() throws Exception {
        // when
        DatabaseInstanceMapper actual = hibernateConfiguration.createDatabaseInstanceMapper(
            databaseTypeClassNameService
        );

        // then
        assertThat(actual).isNotNull();
    }
}
