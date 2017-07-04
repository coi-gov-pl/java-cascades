package pl.gov.coi.cascades.server.domain.loadtemplate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.slf4j.Logger;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;

import java.io.InputStream;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 30.06.17
 */
public class ZipArchiveTest {

    @Mock
    private Upload upload;

    @Mock
    private InputStream inputStream;

    @Mock
    private Logger logger;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testGetPath() throws Exception {
        // given
        ZipArchive zipArchive = new ZipArchive(
            upload,
            logger
        );

        // when
        Path actual = zipArchive.getPath();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.toString()).contains("LoadTemplateZipArchive");
    }

    @Test
    public void testEnsureUnzipped() throws Exception {
        // given
        ZipArchive zipArchive = new ZipArchive(
            upload,
            logger
        );
        when(upload.getInputStream()).thenReturn(null);

        // then
        expectedException.expect(EidIllegalStateException.class);
        expectedException.expectMessage("20170605:113002");

        // when
        zipArchive.ensureUnzipped();
    }

    @Test
    public void testGetUpload() throws Exception {
        // given
        Long size = 5L;
        String content = "application/zip";
        Upload upload = new Upload(
            inputStream,
            size,
            content
        );
        ZipArchive zipArchive = new ZipArchive(
            upload,
            logger
        );

        // when
        Upload actual = zipArchive.getUpload();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getInputStream()).isEqualTo(inputStream);
        assertThat(actual.getSize()).isEqualTo(size);
        assertThat(actual.getContentType()).isEqualTo(content);
    }

}
