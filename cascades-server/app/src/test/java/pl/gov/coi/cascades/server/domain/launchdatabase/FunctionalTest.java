package pl.gov.coi.cascades.server.domain.launchdatabase;

import org.assertj.core.description.Description;
import org.assertj.core.description.TextDescription;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.gov.coi.cascades.server.Environment;
import pl.gov.coi.cascades.server.persistance.stub.DatabaseTypeStub;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 14.03.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(Environment.DEVELOPMENT_NAME)
public class FunctionalTest {

    @Inject
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(this.wac)
            .build();
    }

    @Test
    public void testPositivePath() throws Exception {
        // given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/databases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(properRequest());

        // when
        MvcResult result = mockMvc.perform(requestBuilder)
            .andReturn();
        MockHttpServletResponse response = result.getResponse();

        // then
        assertThat(response.getStatus())
            .as(buildDescription(response))
            .isEqualTo(200);
    }

    private Description buildDescription(MockHttpServletResponse response)
        throws UnsupportedEncodingException {

        String content = response.getContentAsString();
        return new TextDescription("Response BODY is:\n\n" + content);
    }

    private String properRequest() throws JSONException {
        DatabaseTypeStub stub = new DatabaseTypeStub();
        return new JSONObject()
            .put("type", stub.getName())
            .toString();
    }

}
