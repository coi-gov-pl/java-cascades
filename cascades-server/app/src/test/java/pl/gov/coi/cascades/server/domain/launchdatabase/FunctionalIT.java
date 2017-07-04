package pl.gov.coi.cascades.server.domain.launchdatabase;

import org.assertj.core.description.Description;
import org.assertj.core.description.TextDescription;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.gov.coi.cascades.server.StubDevelopmentTest;
import pl.gov.coi.cascades.server.persistance.stub.DatabaseTypeStub;
import pl.gov.coi.cascades.server.persistance.stub.TemplateIdGatewayStub;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 14.03.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@StubDevelopmentTest
public class FunctionalIT {

    private static final String ORACLE_TEMPLATE = "oracle_template";
    private static final String NON_EXISTING_TEMPLATE_ID = "non_existing_template_id";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Inject
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(this.wac)
            .build();
    }

    @After
    public void after() {
        TemplateIdGatewayStub.getAllTemplates().put(
            TemplateIdGatewayStub.TEMPLATE_ID3.getId(),
            TemplateIdGatewayStub.TEMPLATE_ID3
        );
    }

    @Test
    public void testPathWithPresentDefaultTemplate() throws Exception {
        // given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/databases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestWithNoTemplateId());

        // when
        MvcResult result = mockMvc.perform(requestBuilder)
            .andReturn();
        MockHttpServletResponse response = result.getResponse();

        // then
        assertThat(response.getStatus())
            .as(buildDescription(response))
            .isEqualTo(200);
    }

    @Test
    public void testPathWithPresentGivenTemplateId() throws Exception {
        // given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/databases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestWithTemplateId(ORACLE_TEMPLATE));

        // when
        MvcResult result = mockMvc.perform(requestBuilder)
            .andReturn();
        MockHttpServletResponse response = result.getResponse();

        // then
        assertThat(response.getStatus())
            .as(buildDescription(response))
            .isEqualTo(200);
    }

    @Test
    public void testPathWithNotPresentGivenTemplateId() throws Exception {
        // given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/databases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestWithTemplateId(NON_EXISTING_TEMPLATE_ID));
        TemplateIdGatewayStub.getAllTemplates().remove(TemplateIdGatewayStub.TEMPLATE_ID3.getId());

        // when
        MvcResult result = mockMvc.perform(requestBuilder)
            .andReturn();
        MockHttpServletResponse response = result.getResponse();

        // then
        assertThat(response.getStatus())
            .as(buildDescription(response))
            .isEqualTo(400);
    }

    private Description buildDescription(MockHttpServletResponse response)
        throws UnsupportedEncodingException {

        String content = response.getContentAsString();
        return new TextDescription("Response BODY is:\n\n" + content);
    }

    private String requestWithNoTemplateId() throws JSONException {
        DatabaseTypeStub stub = new DatabaseTypeStub();
        return new JSONObject()
            .put("type", stub.getName())
            .toString();
    }

    private String requestWithTemplateId(String templateId) throws JSONException {
        DatabaseTypeStub stub = new DatabaseTypeStub();
        return new JSONObject()
            .put("type", stub.getName())
            .put("templateId", templateId)
            .toString();
    }

}
