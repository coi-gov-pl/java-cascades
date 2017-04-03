package pl.gov.coi.cascades.server.domain.deletedatabase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
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
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.server.Environment;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.persistance.hibernate.UserGatewayImpl;

import javax.inject.Inject;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 20.03.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(Environment.DEVELOPMENT_NAME)
public class FunctionalTest {

    private static final String NOT_EXISTING_ID = "3253v4v4363";
    private MockMvc mockMvc;

    @Inject
    private WebApplicationContext wac;

    private UserGatewayImpl userGateway;

    @Inject
    public void setUserGateway(UserGatewayImpl userGateway) {
        this.userGateway = userGateway;
    }

    @Mock
    private DatabaseInstance databaseInstance;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testUserGatewayPositivePath() throws Exception {
        // when
        Optional<User> actual = userGateway.find("jrambo");

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get().getUsername()).isEqualTo("jrambo");
        assertThat(actual.get().getEmail()).isEqualTo("jrambo@example.org");
        assertThat(actual.get().getDatabases()).hasSize(1);
    }

    @Test
    public void testUserGatewayNegativePath() throws Exception {
        // when
        Optional<User> actual = userGateway.find("nonExistingUser");

        // then
        assertThat(actual).isEmpty();
    }

    @Test
    public void testPositivePath() throws Exception {
        // given
        Optional<User> actual = userGateway.find("jrambo");
        DatabaseId databaseId = actual.get().getDatabases().iterator().next().getDatabaseId();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .delete("/databases/" + databaseId.getId())
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
            .delete("/databases/" + NOT_EXISTING_ID)
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
