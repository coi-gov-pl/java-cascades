package pl.gov.coi.cascades.server.domain.loadtemplate;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 08.06.17.
 */
public class ValidatorTest {

    private Validator validator;
    private String id = "template_id";
    private String jsonName = "test.json";
    private String serverId = "1234";
    private String status = "created";
    private String version = "0.0.1";

    @Mock
    private Request request;

    @Mock
    private Response response;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        validator = new Validator(
            response,
            request,
            id,
            true,
            serverId,
            status,
            version,
            jsonName
        );
    }

    @Test
    public void testGetId() throws Exception {
        // when
        String actual = validator.getId();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(id);
    }

    @Test
    public void testIsDefault() throws Exception {
        // when
        boolean actual = validator.isDefault();

        // then
        assertThat(actual).isTrue();
    }

    @Test
    public void testGetStatus() throws Exception {
        // when
        String actual = validator.getStatus();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(status);
    }

    @Test
    public void testGetServerId() throws Exception {
        // when
        String actual = validator.getServerId();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(serverId);
    }

    @Test
    public void testGetVersion() throws Exception {
        // when
        String actual = validator.getVersion();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(version);
    }

    @Test
    public void testBuilder() throws Exception {
        // when
        Validator validatorBuilder = Validator.builder()
            .id(id)
            .isDefault(true)
            .jsonFilename(jsonName)
            .request(request)
            .response(response)
            .serverId(serverId)
            .status(status)
            .version(version)
            .build();

        // then
        assertThat(validatorBuilder).isNotNull();
    }

}
