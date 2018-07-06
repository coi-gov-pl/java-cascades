package pl.gov.coi.cascades.server.domain.launchdatabase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.contract.domain.TemplateIdStatus;
import pl.gov.coi.cascades.server.domain.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.02.17.
 */
public class RequestTest {

    private final static String instanceName = "PESEL";
    private final static String ID = "453v4c4c";
    private final static String NAME = "dg5nj69s";
    private final static TemplateIdStatus TEMPLATE_ID_STATUS = TemplateIdStatus.CREATED;
    private final static boolean IS_DEFAULT = true;
    private final static String SERVER_ID = "5345c3";
    private final static String VERSION = "0.0.1";
    private Request request;
    private Template template;

    @Mock
    private User user;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        template = new Template(
            ID,
            NAME,
            TEMPLATE_ID_STATUS,
            IS_DEFAULT,
            SERVER_ID,
            VERSION
        );
        request = new Request(
            user,
            template,
            instanceName
        );
    }

    @Test
    public void testDefaultConstructor() throws Exception {
        // when
        Request actual = new Request(
            user,
            template,
            instanceName
        );

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testGetTemplateId() throws Exception {
        // when
        Optional<String> actual = request.getTemplateId();

        // then
        assertThat(actual).isEqualTo(Optional.of(ID));
    }

    @Test
    public void testGetInstanceName() throws Exception {
        // when
        Optional<String> actual = request.getInstanceName();

        // then
        assertThat(actual).isEqualTo(Optional.of(instanceName));
    }

    @Test
    public void testBuilder() {
        // when
        Request requestBuilder = Request.builder()
            .instanceName(instanceName)
            .user(user)
            .template(template)
            .build();

        // then
        assertThat(requestBuilder).isNotNull();
    }

    @Test
    public void testGetUser() throws Exception {
        // when
        User actual = request.getUser();

        // then
        assertThat(actual).isNotNull();
    }
}
