package pl.gov.coi.cascades.server.persistance.hibernate;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.server.domain.DatabaseIdGateway;
import pl.gov.coi.cascades.server.domain.DatabaseTemplateGateway;
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
    public void testCreateDatabaseTemplateGateway() throws Exception {
        // given
        HibernateConfiguration hibernateConfiguration = new HibernateConfiguration();

        // when
        DatabaseTemplateGateway actual = hibernateConfiguration.createDatabaseTemplateGateway();

        // then
        assertThat(actual).isInstanceOf(DatabaseTemplateGateway.class);
    }

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

}
