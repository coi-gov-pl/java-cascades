package pl.gov.coi.cascades.server.presentation.deletedatabase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.HttpStatus;
import pl.gov.coi.cascades.server.domain.Error;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 22.03.17.
 */
public class PresenterTest {

    private Presenter presenter;

    @Mock
    private Error error;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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
        // when
        presenter.addError(error);

        // then
        assertThat(presenter.getErrors()).isNotNull();
        assertThat(presenter.getErrors()).hasSize(1);
    }

    @Test
    public void testGetErrors() throws Exception {
        // given
        presenter.addError(error);

        // when
        Iterable<Error> errors = presenter.getErrors();

        // then
        assertThat(errors).isNotNull();
        assertThat(errors).contains(error);
    }

    @Test
    public void testCreateModelWhenIsOk() throws Exception {
        // when
        ViewModel actual = presenter.createModel();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testCreateModelWhenIsBadRequest() throws Exception {
        // given
        presenter.addError(error);

        // when
        ViewModel actual = presenter.createModel();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
