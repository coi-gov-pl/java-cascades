package pl.gov.coi.cascades.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.server.domain.DatabaseIdGateway;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseStatus;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.UserGateway;

import javax.inject.Inject;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 04.04.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({Environment.DEVELOPMENT_NAME, Environment.HIBERNATE_NAME})
public class DatabaseIdGatewayImplFunctionalTest {

    private static final String NON_EXISTING_DATABASE_ID = "875785887";

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
        Optional<User> user = userGateway.find("jrambo");
        DatabaseId databaseId = user.get().getDatabases().iterator().next().getDatabaseId();
        Optional<DatabaseInstance> actual = databaseIdGateway.findInstance(databaseId);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get().getDatabaseId().getId()).isEqualTo(databaseId.getId());
        assertThat(actual.get().getTemplateId().getId()).isEqualTo("postgres");
        assertThat(actual.get().getInstanceName()).isEqualTo("Postgres is *%! hard");
        assertThat(actual.get().getReuseTimes()).isEqualTo(1);
        assertThat(actual.get().getCredentials().getUsername()).isEqualTo("fdrg5yh545y");
        assertThat(actual.get().getNetworkBind().getHost()).isEqualTo("cascades.example.org");
        assertThat(actual.get().getNetworkBind().getPort()).isEqualTo(443);
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
