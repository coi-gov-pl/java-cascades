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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.04.17.
 */
public class HibernateConfigurationTest {

    @Mock
    private DatabaseTypeClassNameService databaseTypeClassNameService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testCreateTemplateIdGateway() throws Exception {
        // given
        HibernateConfiguration hibernateConfiguration = new HibernateConfiguration();

        // when
        TemplateIdGateway actual = hibernateConfiguration.createTemplateIdGateway();

        // then
        assertThat(actual).isInstanceOf(TemplateIdGatewayImpl.class);
    }

    @Test
    public void testCreateUserGateway() throws Exception {
        // given
        HibernateConfiguration hibernateConfiguration = new HibernateConfiguration();

        // when
        UserGateway actual = hibernateConfiguration.createUserGateway(
            databaseTypeClassNameService
        );

        // then
        assertThat(actual).isInstanceOf(UserGatewayImpl.class);
    }

    @Test
    public void testCreateDatabaseIdGateway() throws Exception {
        // given
        HibernateConfiguration hibernateConfiguration = new HibernateConfiguration();

        // when
        DatabaseIdGateway actual = hibernateConfiguration.createDatabaseIdGateway(
            databaseTypeClassNameService
        );

        // then
        assertThat(actual).isInstanceOf(DatabaseIdGatewayImpl.class);
    }

    @Test
    public void testDatabaseInstanceGateway() throws Exception {
        // given
        HibernateConfiguration hibernateConfiguration = new HibernateConfiguration();

        // when
        DatabaseInstanceGateway actual = hibernateConfiguration.createDatabaseInstanceGateway(
            databaseTypeClassNameService
        );

        // then
        assertThat(actual).isInstanceOf(DatabaseInstanceGatewayImpl.class);
    }

    @Test
    public void testDatabaseLimitGateway() throws Exception {
        // given
        HibernateConfiguration hibernateConfiguration = new HibernateConfiguration();

        // when
        DatabaseLimitGateway actual = hibernateConfiguration.createDatabaseLimitGateway(
            databaseTypeClassNameService
        );

        // then
        assertThat(actual).isInstanceOf(DatabaseLimitGatewayImpl.class);
    }
}
