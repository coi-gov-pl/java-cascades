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

    private HibernateConfiguration hibernateConfiguration = new HibernateConfiguration();

    @Test
    public void testCreateTemplateIdGateway() {
        // when
        TemplateIdGateway actual = hibernateConfiguration.createTemplateIdGateway();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testCreateUserGateway() {
        // when
        UserGateway actual = hibernateConfiguration.createUserGateway(
            databaseTypeClassNameService
        );

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testCreateDatabaseIdGateway() {
        // when
        DatabaseIdGateway actual = hibernateConfiguration.createDatabaseIdGateway(
            databaseTypeClassNameService
        );

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testCreateDatabaseInstanceGateway() {
        // when
        DatabaseInstanceGateway actual = hibernateConfiguration.createDatabaseInstanceGateway();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testCreateDatabaseLimitGateway() {
        // when
        DatabaseLimitGateway actual = hibernateConfiguration.createDatabaseLimitGateway();

        // then
        assertThat(actual).isNotNull();
    }

}
