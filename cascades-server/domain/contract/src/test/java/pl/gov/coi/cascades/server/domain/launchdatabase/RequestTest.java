package pl.gov.coi.cascades.server.domain.launchdatabase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.contract.domain.TemplateIdStatus;
import pl.gov.coi.cascades.server.domain.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.02.17.
 */
public class RequestTest {

    private final static String typeClassName = "Type";
    private final static String instanceName = "PESEL";
    private final static String ID = "453v4c4c";
    private final static TemplateIdStatus TEMPLATE_ID_STATUS = TemplateIdStatus.CREATED;
    private final static boolean IS_DEFAULT = true;
    private final static String SERVER_ID = "5345c3";
    private Request request;
    private TemplateId templateId;

    @Mock
    private User user;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        templateId = new TemplateId(
            ID,
            TEMPLATE_ID_STATUS,
            IS_DEFAULT,
            SERVER_ID
        );
        request = new Request(
            typeClassName,
            user,
            templateId,
            instanceName
        );
    }

    @Test
    public void testDefaultConstructor() throws Exception {
        // when
        Request actual = new Request(
            typeClassName,
            user,
            templateId,
            instanceName
        );

        // then
        assertNotNull(actual);
    }

    @Test
    public void testGetTemplateId() throws Exception {
        // when
        Optional<String> actual = request.getTemplateId();

        // then
        assertThat(actual).isEqualTo(Optional.ofNullable(ID));
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
            .type(typeClassName)
            .templateId(templateId)
            .build();

        // then
        assertThat(requestBuilder).isNotNull();
    }

}
