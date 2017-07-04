package pl.gov.coi.cascades.server.domain.loadtemplate;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 29.06.17
 */
public class UnzippedValidatorTest {

    private Path tempFolder;
    private Path zipPath;

    @Mock
    private ZipArchive zipArchive;

    @Mock
    private Upload upload;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        Path currentRelativePath = Paths.get("");
        tempFolder = folder.getRoot().toPath();
        zipPath = currentRelativePath.toAbsolutePath().resolve("src").resolve("test").resolve("resources");
    }

    @Test
    public void testIsValidWhenGivenPathIsNotPresent() throws Exception {
        // given
        Path nonExistingPath = tempFolder.resolve("nonExistingDirectory");
        UnzippedValidator unzippedValidator = new UnzippedValidator(
            zipArchive,
            nonExistingPath
        );
        when(zipArchive.getUpload()).thenReturn(upload);
        InputStream is = new FileInputStream(zipPath.resolve("test12.zip").toFile());
        when(zipArchive.getUpload().getInputStream()).thenReturn(is);
        when(zipArchive.getUpload()).thenReturn(upload);

        // then
        expectedException.expectMessage(containsString("20170628:104151"));

        // when
        unzippedValidator.isValid();
    }

}
