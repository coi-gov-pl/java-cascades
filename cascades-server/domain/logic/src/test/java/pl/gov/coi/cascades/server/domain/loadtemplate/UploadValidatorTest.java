package pl.gov.coi.cascades.server.domain.loadtemplate;

import lombok.Getter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.service.Violation;

import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 29.06.17
 */
public class UploadValidatorTest {

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

    @Test
    public void testIsNotValid() throws Exception {
        // given
        String content = "application/rar";
        Long size = 50L;
        Path path = folder.getRoot().toPath();
        ResponseImpl response = new ResponseImpl();
        UploadValidator uploadValidator = new UploadValidator(
            zipArchive
        );
        when(zipArchive.getUpload()).thenReturn(upload);
        when(zipArchive.getUpload().getContentType()).thenReturn(content);
        when(zipArchive.getPath()).thenReturn(path);
        when(zipArchive.getUpload().getSize()).thenReturn(size);
        uploadValidator.addViolationListener(response::addViolation);

        // when
        boolean actual = uploadValidator.isValid();

        // then
        assertThat(actual).isFalse();
        assertThat(response.getViolations()).hasSize(1);
        boolean containsField = false;
        for(Violation violation : response.getViolations()) {
            containsField = violation.getMessage().contains(
                "Loaded file is in the wrong format."
            );
        }
        assertThat(containsField).isTrue();
    }

    @Test
    public void testIsValid() {
        // given
        String content = "application/zip";
        Long size = 50L;
        Path path = folder.getRoot().toPath();
        ResponseImpl response = new ResponseImpl();
        UploadValidator uploadValidator = new UploadValidator(
            zipArchive
        );
        when(zipArchive.getUpload()).thenReturn(upload);
        when(zipArchive.getUpload().getContentType()).thenReturn(content);
        when(zipArchive.getPath()).thenReturn(path);
        when(zipArchive.getUpload().getSize()).thenReturn(size);
        uploadValidator.addViolationListener(response::addViolation);

        // when
        boolean actual = uploadValidator.isValid();

        // then
        assertThat(actual).isTrue();
        assertThat(response.getViolations()).hasSize(0);
    }

    private static final class ResponseImpl implements Response {

        @Getter
        private final Collection<Violation> violations = new HashSet<>();
        @Getter
        private String id;
        @Getter
        private String name;
        @Getter
        private String status;
        @Getter
        private boolean isDefault;
        @Getter
        private String serverId;
        @Getter
        private String version;

        @Override
        public void addViolation(Violation violation) {
            violations.add(violation);
        }

        @Override
        public boolean isSuccessful() {
            return violations.isEmpty();
        }

        @Override
        public void setId(String id) {
            this.id = id;
        }

        @Override
        public void setName(String name) {
            this.name = name;
        }

        @Override
        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public void setDefault(boolean isDefault) {
            this.isDefault = isDefault;
        }

        @Override
        public void setServerId(String versionId) {
            this.serverId = serverId;
        }

        @Override
        public void setVersion(String version) {
            this.version = version;
        }
    }

}
