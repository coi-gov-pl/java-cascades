package pl.gov.coi.cascades.server.domain.deletedatabase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.server.domain.User;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.04.17.
 */
public class RequestTest {

    private final static String ID = "453v4c4c";
    private Request request;
    private DatabaseId databaseId;

    @Mock
    private User user;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        databaseId = new DatabaseId(
            ID
        );
        request = new Request(
            databaseId,
            user
        );
    }

    @Test
    public void testGetDatabaseId() throws Exception {
        // when
        DatabaseId actual = request.getDatabaseId();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testGetUser() throws Exception {
        // when
        User actual = request.getUser();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testBuilder() throws Exception {
        // when
        Request requestBuilder = Request.builder()
            .user(user)
            .databaseId(databaseId)
            .build();

        // then
        assertThat(requestBuilder).isNotNull();
    }

}
