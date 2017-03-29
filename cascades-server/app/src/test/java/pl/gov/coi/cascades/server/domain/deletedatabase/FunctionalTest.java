package pl.gov.coi.cascades.server.domain.deletedatabase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.gov.coi.cascades.server.Environment;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 20.03.17.
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
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testPositivePath() throws Exception {
        // given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .delete("/databases/19")
            .contentType(MediaType.APPLICATION_JSON)
            .content(properRequest());

        // when
        MvcResult result = mockMvc.perform(requestBuilder)
            .andReturn();

        // then
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    public void testNegativePath() throws Exception {
        // given
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .delete("/databases/notExistingId")
            .contentType(MediaType.APPLICATION_JSON)
            .content(properRequest());

        // when
        MvcResult result = mockMvc.perform(requestBuilder)
            .andReturn();

        // then
        assertThat(result.getResponse().getStatus()).isEqualTo(400);
        assertThat(result.getResponse().getContentAsString()).contains("Given database id is not present.");
    }

    private String properRequest() throws JSONException {
        return new JSONObject()
            .toString();
    }

}
