package pl.gov.coi.cascades.server.domain.loadtemplate;

import com.google.common.io.ByteStreams;
import org.assertj.core.description.Description;
import org.assertj.core.description.TextDescription;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.gov.coi.cascades.server.StubDevelopmentTest;

import javax.inject.Inject;

import java.io.FileInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 23.06.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@StubDevelopmentTest
public class FunctionalIT {

    private String path;
    private static final String TEST = "src/test/resources";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Inject
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        Path currentRelativePath = Paths.get("");
        path = currentRelativePath.toAbsolutePath().toString() + File.separator + TEST + File.separator;
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(this.wac)
            .build();
    }

    @Test
    public void testLoadingZipFileWithErrors() throws Exception {
        // given
        String fileName = "incorrectZip.zip";
        MockMultipartFile file = new MockMultipartFile("file", fileName,"application/zip", correctRequest(fileName));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .fileUpload("")
            .file(file);

        // when
        MvcResult result = mockMvc.perform(requestBuilder)
            .andReturn();
        MockHttpServletResponse response = result.getResponse();

        // then
        assertThat(response.getStatus())
            .as(buildDescription(response))
            .isEqualTo(400);
    }

    @Test
    public void testLoadingZipFileWithNoErrors() throws Exception {
        // given
        String fileName = "correctZip.zip";
        MockMultipartFile file = new MockMultipartFile("file", fileName,"application/zip", correctRequest(fileName));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .fileUpload("")
            .file(file);

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

    private byte[] correctRequest(String filename) throws IOException {
        InputStream is = new FileInputStream(new File(path + filename));
        return ByteStreams.toByteArray(is);
    }
}
