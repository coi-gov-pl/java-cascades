package pl.gov.coi.cascades.server.domain.loadtemplate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 14.06.17.
 */
public class RequestTest {

    private String name = "oracle_template";
    private Long size = 43243L;
    private boolean isEmpty = false;
    private Upload upload;
    private String contentType = "application/zip";

    @Mock
    private InputStream zipFile;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testDefaultConstructor() throws Exception {
        // when
        Upload upload = Upload.builder()
            .contentType(contentType)
            .inputStream(zipFile)
            .size(size)
            .build();

        Request actual = new Request(
            name,
            isEmpty,
            upload
        );

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testGetInputStream() throws Exception {
        // given
        Upload upload = new Upload(
            zipFile,
            size,
            contentType
        );
        Request request = new Request(
            name,
            isEmpty,
            upload
        );

        // when
        InputStream actual = request.getUpload().getInputStream();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testGetName() throws Exception {
        // given
        Upload upload = new Upload(
            zipFile,
            size,
            contentType
        );
        Request request = new Request(
            name,
            isEmpty,
            upload
        );

        // when
        String actual = request.getName();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testGetSize() throws Exception {
        // given
        Upload upload = new Upload(
            zipFile,
            size,
            contentType
        );
        Request request = new Request(
            name,
            isEmpty,
            upload
        );

        // when
        Long actual = request.getUpload().getSize();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testIsEmpty() throws Exception {
        // given
        Upload upload = new Upload(
            zipFile,
            size,
            contentType
        );
        Request request = new Request(
            name,
            isEmpty,
            upload
        );

        // when
        boolean actual = request.isEmpty();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testGetContentType() throws Exception {
        // given
        Upload upload = new Upload(
            zipFile,
            size,
            contentType
        );
        Request request = new Request(
            name,
            isEmpty,
            upload
        );

        // when
        String actual = request.getUpload().getContentType();

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testBuilder() throws Exception {
        // when
        Upload upload = Upload.builder()
            .size(size)
            .inputStream(zipFile)
            .contentType(contentType)
            .build();
        Request requestBuilder = Request.builder()
            .isEmpty(isEmpty)
            .name(name)
            .upload(upload)
            .build();

        // then
        assertThat(requestBuilder).isNotNull();
        assertThat(requestBuilder.getUpload()).isNotNull();
        assertThat(requestBuilder.getUpload().getContentType()).isEqualTo(contentType);
        assertThat(requestBuilder.getUpload().getSize()).isEqualTo(size);
        assertThat(requestBuilder.getUpload().getInputStream()).isEqualTo(zipFile);
        assertThat(requestBuilder.isEmpty()).isFalse();
    }

}
