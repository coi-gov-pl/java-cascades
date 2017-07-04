package pl.gov.coi.cascades.server.presentation.loadtemplate;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import pl.gov.coi.cascades.contract.service.WithViolations;
import pl.gov.coi.cascades.server.domain.loadtemplate.UseCase;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 14.06.17.
 */
public class LoadTemplateControllerTest {

    @Mock
    private InputStream inputStream;

    @Mock
    private MultipartFile request;

    @Mock
    private UseCase useCase;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testCheckTemplate() throws Exception {
        // given
        LoadTemplateController loadTemplateController = new LoadTemplateController(
            useCase
        );
        String contentType = "application/zip";
        when(request.getContentType()).thenReturn(contentType);
        when(request.getInputStream()).thenReturn(inputStream);
        String name = "oracle-template";
        when(request.getOriginalFilename()).thenReturn(name);
        Long size = 464363L;
        when(request.getSize()).thenReturn(size);
        when(request.isEmpty()).thenReturn(false);

        // when
        ResponseEntity<WithViolations<RemoteTemplateSpec>> actual = loadTemplateController.checkTemplate(
            request
        );

        // then
        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getHeaders()).hasSize(0);
        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody().getViolations()).hasSize(0);
    }

    @Test
    public void testLoadTemplate() throws Exception {
        // given
        String upload = "upload";
        LoadTemplateController loadTemplateController = new LoadTemplateController(
            useCase
        );

        // when
        String actual = loadTemplateController.loadTemplate();

        // then
        assertThat(actual).isEqualTo(upload);
    }

}
