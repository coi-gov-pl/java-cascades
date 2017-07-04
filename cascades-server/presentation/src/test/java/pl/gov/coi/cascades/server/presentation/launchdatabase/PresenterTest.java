package pl.gov.coi.cascades.server.presentation.launchdatabase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.HttpStatus;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;
import pl.gov.coi.cascades.contract.service.Violation;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.04.17.
 */
public class PresenterTest {

    private Presenter presenter;

    @Mock
    private DatabaseId databaseId;

    @Mock
    private DatabaseType databaseType;

    @Mock
    private NetworkBind networkBind;

    @Mock
    private UsernameAndPasswordCredentials credentials;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        presenter = new Presenter();
    }

    @Test
    public void testIsSuccessful() throws Exception {
        // when
        boolean actual = presenter.isSuccessful();

        // then
        assertThat(actual).isTrue();
    }

    @Test
    public void testAddError() throws Exception {
        // given
        Violation violation = new ViolationTest();

        // when
        presenter.addError(violation);
        ViewModel actual = presenter.createModel();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getBody().getViolations()).hasSize(1);
    }

    @Test
    public void testSetDatabaseId() throws Exception {
        // when
        presenter.setDatabaseId(databaseId);
        ViewModel actual = presenter.createModel();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getBody().getTarget()).isNotNull();
    }

    @Test
    public void testSetNetworkBind() throws Exception {
        // when
        presenter.setNetworkBind(networkBind);
        ViewModel actual = presenter.createModel();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getBody().getTarget()).isNotNull();
        assertThat(actual.getBody().getTarget().getNetworkBind()).isNotNull();
        assertThat(actual.getBody().getTarget().getNetworkBind()).isEqualTo(networkBind);
    }

    @Test
    public void testSetCredentials() throws Exception {
        // when
        presenter.setCredentials(credentials);
        ViewModel actual = presenter.createModel();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getBody().getTarget()).isNotNull();
        assertThat(actual.getBody().getTarget().getCredentials()).isNotNull();
        assertThat(actual.getBody().getTarget().getCredentials()).isEqualTo(credentials);
    }

    @Test
    public void testSetDatabaseName() throws Exception {
        // when
        String databaseName = "oracle_database";
        presenter.setDatabaseName(databaseName);
        ViewModel actual = presenter.createModel();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getBody().getTarget()).isNotNull();
        assertThat(actual.getBody().getTarget().getDatabaseName()).isNotNull();
        assertThat(actual.getBody().getTarget().getDatabaseName()).isEqualTo(databaseName);
    }

    @Test
    public void testCreateModelWhenIsSuccessful() throws Exception {
        // when
        ViewModel actual = presenter.createModel();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testCreateModelWhenIsNotSuccessful() throws Exception {
        // given
        Violation violation = new ViolationTest();
        presenter.addError(violation);

        // when
        ViewModel actual = presenter.createModel();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private static final class ViolationTest implements Violation {

        private String message = "Violation test error";
        private String propertyPath = "ViolationTest";

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public String getPropertyPath() {
            return propertyPath;
        }
    }

}
