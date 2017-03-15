package pl.gov.coi.cascades.server.domain.launchdatabase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.gov.coi.cascades.server.persistance.stub.DatabaseTypeStub;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 14.03.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FunctionalTest {

    @Inject
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
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

        // then
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }

    private String properRequest() throws JSONException {
        return new JSONObject()
            .put("typeClassName", DatabaseTypeStub.class.getName())
            .put("templateId", "templateId1")
            .toString();
    }

}
