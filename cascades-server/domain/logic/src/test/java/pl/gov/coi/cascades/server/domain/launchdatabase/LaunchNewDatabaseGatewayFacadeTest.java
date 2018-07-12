package pl.gov.coi.cascades.server.domain.launchdatabase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseInstanceGateway;
import pl.gov.coi.cascades.server.domain.DatabaseLimitGateway;
import pl.gov.coi.cascades.server.domain.DatabaseOperationsGateway;
import pl.gov.coi.cascades.server.domain.DatabaseUserGateway;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.UserGateway;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.04.17.
 */
public class LaunchNewDatabaseGatewayFacadeTest {

    private LaunchNewDatabaseGatewayFacade facade;

    @Mock
    private TemplateIdGateway templateIdGateway;

    @Mock
    private UserGateway userGateway;

    @Mock
    private User user;

    @Mock
    private DatabaseInstance databaseInstance;

    @Mock
    private DatabaseLimitGateway databaseLimitGateway;

    @Mock
    private DatabaseInstanceGateway databaseInstanceGateway;

    @Mock
    private DatabaseUserGateway databaseUserGateway;

    @Mock
    private DatabaseOperationsGateway databaseOperations;

    @Mock
    private Template template;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        facade = new LaunchNewDatabaseGatewayFacade(
            templateIdGateway,
            userGateway,
            databaseLimitGateway,
            databaseInstanceGateway,
            databaseOperations,
            databaseUserGateway
        );
    }

    @Test
    public void testFindTemplateId() throws Exception {
        // given
        String template = "oracle_template";
        when(templateIdGateway.find(anyString())).thenReturn(Optional.of(this.template));

        // when
        Optional<Template> actual = facade.findTemplateId(template);

        // then
        assertThat(actual).isNotNull();
        verify(templateIdGateway, times(1)).find(anyString());
    }

    @Test
    public void testGetDefaultTemplateId() throws Exception {
        // given
        when(templateIdGateway.getDefaultTemplateId()).thenReturn(Optional.of(template));

        // when
        Optional<Template> actual = facade.getDefaultTemplateId();

        // then
        assertThat(actual).isNotNull();
        verify(templateIdGateway, times(1)).getDefaultTemplateId();
    }

    @Test
    public void testFindUser() throws Exception {
        // given
        String userName = "Jan Kowalski";
        when(userGateway.find(anyString())).thenReturn(Optional.of(user));

        // when
        Optional<User> actual = facade.findUser(userName);

        // then
        assertThat(actual).isNotNull();
        verify(userGateway, times(1)).find(anyString());
    }

    @Test
    public void testSave() throws Exception {
        // when
        facade.save(user);

        // then
        verify(userGateway, times(1)).save(any(User.class));
    }

    @Test
    public void testLaunchDatabase() throws Exception {
        //
        when(databaseInstanceGateway.save(any(DatabaseInstance.class))).thenReturn(databaseInstance);
        when(databaseOperations.createDatabase(any(DatabaseInstance.class))).thenReturn(databaseInstance);

        // when
        DatabaseInstance actual = facade.launchDatabase(databaseInstance);

        // then
        assertThat(actual).isNotNull();
        verify(databaseInstanceGateway, times(1)).save(any(DatabaseInstance.class));
        verify(databaseOperations, times(1)).createDatabase(any(DatabaseInstance.class));
    }

    @Test
    public void testGetDatabaseLimitGateway() throws Exception {
        // when
        DatabaseLimitGateway actual = facade.getDatabaseLimitGateway();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isInstanceOf(DatabaseLimitGateway.class);
    }

    @Test
    public void testGetTemplateIdGateway() throws Exception {
        // when
        TemplateIdGateway actual = facade.getTemplateIdGateway();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isInstanceOf(TemplateIdGateway.class);
    }

}
