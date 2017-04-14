package pl.gov.coi.cascades.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.server.domain.DatabaseIdGateway;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseStatus;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.UserGateway;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateIdStatus;

import javax.inject.Inject;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 04.04.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@HibernateDevelopmentTest
public class DatabaseIdGatewayImplFunctionalIT {

    private static final String NON_EXISTING_DATABASE_ID = "875785887";
    private static final String SERVER_ID = "ukdtksu6w66j";
    private static final String EXISTING_USER = "jrambo";
    private static final String INSTANCE_NAME = "Postgres is *%! hard";
    private static final String USERNAME = "fdrg5yh545y";
    private static final String HOST = "cascades.example.org";
    private static final int PORT = 443;

    @Inject
    private UserGateway userGateway;

    private DatabaseIdGateway databaseIdGateway;

    @Inject
    public void setDatabaseIdGateway(DatabaseIdGateway databaseIdGateway) {
        this.databaseIdGateway = databaseIdGateway;
    }

    @Test
    public void testUserGatewayPositivePath() throws Exception {
        // when
        Optional<User> user = userGateway.find(EXISTING_USER);
        DatabaseId databaseId = user.get().getDatabases().iterator().next().getDatabaseId();
        Optional<DatabaseInstance> actual = databaseIdGateway.findInstance(databaseId);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get().getDatabaseId().getId()).isEqualTo(databaseId.getId());
        assertThat(actual.get().getTemplateId().getServerId()).isEqualTo(SERVER_ID);
        assertThat(actual.get().getTemplateId().getStatus().name()).isEqualTo(TemplateIdStatus.CREATED.name());
        assertThat(actual.get().getTemplateId().isDefault()).isEqualTo(true);
        assertThat(actual.get().getInstanceName()).isEqualTo(INSTANCE_NAME);
        assertThat(actual.get().getReuseTimes()).isEqualTo(1);
        assertThat(actual.get().getCredentials().getUsername()).isEqualTo(USERNAME);
        assertThat(actual.get().getNetworkBind().getHost()).isEqualTo(HOST);
        assertThat(actual.get().getNetworkBind().getPort()).isEqualTo(PORT);
        assertThat(actual.get().getStatus().name()).isEqualTo(DatabaseStatus.LAUNCHED.name());
    }

    @Test
    public void testUserGatewayNegativePath() throws Exception {
        // when
        Optional<DatabaseInstance> actual = databaseIdGateway.findInstance(
            new DatabaseId(NON_EXISTING_DATABASE_ID)
        );

        // then
        assertThat(actual).isEmpty();
    }

}
