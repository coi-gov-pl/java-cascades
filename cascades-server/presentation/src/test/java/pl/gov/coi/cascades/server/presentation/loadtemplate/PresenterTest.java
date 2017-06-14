package pl.gov.coi.cascades.server.presentation.loadtemplate;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.HttpStatus;
import pl.gov.coi.cascades.contract.service.Violation;

import java.util.Collection;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 14.06.17.
 */
public class PresenterTest {

    private Presenter presenter;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        presenter = new Presenter();
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
        presenter.addViolation(violation);

        // when
        ViewModel actual = presenter.createModel();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testAddViolation() throws Exception {
        // given
        Violation violation = new ViolationTest();

        // when
        presenter.addViolation(violation);
        ViewModel actual = presenter.createModel();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getBody().getViolations()).hasSize(1);
    }

    @Test
    public void testIsSuccessful() throws Exception {
        // when
        boolean actual = presenter.isSuccessful();

        // then
        assertThat(actual).isTrue();
    }

    @Test
    public void testSetId() throws Exception {
        // when
        String id = "oracle_template";
        presenter.setId(id);
        ViewModel actual = presenter.createModel();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getBody().getTarget()).isNotNull();
        assertThat(actual.getBody().getTarget().getId()).isNotNull();
        assertThat(actual.getBody().getTarget().getId()).isEqualTo(id);
    }

    @Test
    public void testSetStatus() throws Exception {
        // when
        String status = "created";
        presenter.setStatus(status);
        ViewModel actual = presenter.createModel();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getBody().getTarget()).isNotNull();
        assertThat(actual.getBody().getTarget().getStatus()).isNotNull();
        assertThat(actual.getBody().getTarget().getStatus()).isEqualTo(status);
    }

    @Test
    public void testSetDefault() throws Exception {
        // when
        presenter.setDefault(false);
        ViewModel actual = presenter.createModel();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getBody().getTarget()).isNotNull();
        assertThat(actual.getBody().getTarget().isDefault()).isNotNull();
        assertThat(actual.getBody().getTarget().isDefault()).isFalse();
    }

    @Test
    public void testSetServerId() throws Exception {
        // when
        String serverId = "1234";
        presenter.setServerId(serverId);
        ViewModel actual = presenter.createModel();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getBody().getTarget()).isNotNull();
        assertThat(actual.getBody().getTarget().getServerId()).isNotNull();
        assertThat(actual.getBody().getTarget().getServerId()).isEqualTo(serverId);
    }

    @Test
    public void testSetVersion() throws Exception {
        // when
        String version = "0.0.1";
        presenter.setVersion(version);
        ViewModel actual = presenter.createModel();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getBody().getTarget()).isNotNull();
        assertThat(actual.getBody().getTarget().getVersion()).isNotNull();
        assertThat(actual.getBody().getTarget().getVersion()).isEqualTo(version);
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
