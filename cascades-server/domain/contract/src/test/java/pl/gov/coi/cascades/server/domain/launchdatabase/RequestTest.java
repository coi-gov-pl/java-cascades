package pl.gov.coi.cascades.server.domain.launchdatabase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.server.domain.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.02.17.
 */
public class RequestTest {

    private Request request;
    private String typeClassName;
    private String templateId;
    private String instanceName;

    @Mock
    private User user;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        typeClassName = "Type";
        templateId = "123456abcd";
        instanceName = "PESEL";
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
        assertEquals(Optional.ofNullable(templateId), actual);
    }

    @Test
    public void testGetInstanceName() throws Exception {
        // when
        Optional<String> actual = request.getInstanceName();

        // then
        assertEquals(Optional.ofNullable(instanceName), actual);
    }

    @Test
    public void testBuilder() {
        // when
        Request requestBuilder = Request.builder()
            .instanceName(instanceName)
            .user(user)
            .typeClassName(typeClassName)
            .templateId(templateId)
            .build();

        // then
        assertThat(requestBuilder).isNotNull();
    }

}
