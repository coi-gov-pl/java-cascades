//package pl.gov.coi.cascades.server.domain.loadtemplate;
//
//import lombok.Getter;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import org.junit.rules.TemporaryFolder;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnit;
//import org.mockito.junit.MockitoRule;
//import pl.gov.coi.cascades.contract.service.Violation;
//import pl.wavesoftware.eid.exceptions.EidIllegalStateException;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Collection;
//import java.util.HashSet;
//
//import static java.lang.String.join;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.core.StringContains.containsString;
//import static org.mockito.Mockito.when;
//
///**
// * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
// * @since 08.06.17.
// */
//public class ValidatorTest {
//
//    private String tempFolder;
//    private Validator validator;
//    private String id = "template_id";
//    private String jsonName = "test.json";
//    private String serverId = "1234";
//    private String status = "created";
//    private String version = "0.0.1";
//    private ResponseImpl response;
//    private String zipPath;
//
//    @Mock
//    private Request request;
//
//    @Mock
//    private InputStream zipFile;
//
//    @Rule
//    public TemporaryFolder folder = new TemporaryFolder();
//
//    @Rule
//    public MockitoRule mockitoRule = MockitoJUnit.rule();
//
//    @Rule
//    public ExpectedException expectedException = ExpectedException.none();
//
//    @Before
//    public void setUp() {
//        response = new ResponseImpl();
//        Path currentRelativePath = Paths.get("");
//        tempFolder = folder.getRoot().getPath();
//        zipPath = join(File.separator, currentRelativePath.toAbsolutePath().toString(), "src", "test", "resources");
//        validator = new Validator(
//            response,
//            request,
//            id,
//            true,
//            serverId,
//            status,
//            version,
//            jsonName,
//            false
//        );
//    }
//
//    @After
//    public void after() {
//        folder.delete();
//    }
//
//    @Test
//    public void testReadFileAsStringWhenExOccurred() throws IOException {
//        // given
//        String content = "application/rar";
//        String nonExistingPath = File.separator + "nonExistingDirectory" + File.separator;
//        when(request.getContentType()).thenReturn(content);
//        InputStream is = new FileInputStream(new File(join(File.separator, zipPath, "test12.zip")));
//        when(request.getZipFile()).thenReturn(is);
//        validator.validateIfZipContainsJsonFile(tempFolder);
//
//        // then
//        expectedException.expect(EidIllegalStateException.class);
//        expectedException.expectMessage(containsString("20170614:114408"));
//
//        // when
//        validator.validateJsonFileStructure(nonExistingPath);
//    }
//
//    @Test
//    public void testValidateIfUndeployScriptDoesNotExists() throws IOException {
//        // given
//        InputStream is = new FileInputStream(new File(join(File.separator, zipPath, "test12.zip")));
//        when(request.getZipFile()).thenReturn(is);
//        validator.validateIfZipContainsJsonFile(tempFolder);
//
//        // when
//        validator.validateIfScriptsExist(tempFolder);
//
//        // then
//        assertThat(response.getViolations()).hasSize(1);
//        boolean containsField = false;
//        for(Violation violation : response.getViolations()) {
//            containsField = violation.getMessage().contains(
//                "Missing sql file/files in zip."
//            );
//        }
//        assertThat(containsField).isTrue();
//    }
//
//    @Test
//    public void testValidateIfDeployScriptDoesNotExists() throws IOException {
//        // given
//        InputStream is = new FileInputStream(new File(join(File.separator, zipPath, "test11.zip")));
//        when(request.getZipFile()).thenReturn(is);
//        validator.validateIfZipContainsJsonFile(tempFolder);
//
//        // when
//        validator.validateIfScriptsExist(tempFolder);
//
//        // then
//        assertThat(response.getViolations()).hasSize(1);
//        boolean containsField = false;
//        for(Violation violation : response.getViolations()) {
//            containsField = violation.getMessage().contains(
//                "Missing sql file/files in zip."
//            );
//        }
//        assertThat(containsField).isTrue();
//    }
//
//    @Test
//    public void testValidateWhenUndeployScriptFormatIsNotAppropriate() throws IOException {
//        // given
//        InputStream is = new FileInputStream(new File(join(File.separator, zipPath, "test8.zip")));
//        when(request.getZipFile()).thenReturn(is);
//        validator.validateIfZipContainsJsonFile(tempFolder);
//
//        // when
//        validator.validateScriptsFormat(tempFolder);
//
//        // then
//        assertThat(response.getViolations()).hasSize(1);
//        boolean containsField = false;
//        for(Violation violation : response.getViolations()) {
//            containsField = violation.getMessage().contains(
//                "Deploy and undeploy script must be in .sql format"
//            );
//        }
//        assertThat(containsField).isTrue();
//    }
//
//    @Test
//    public void testValidateWhenDeployScriptFormatIsNotAppropriate() throws IOException {
//        // given
//        InputStream is = new FileInputStream(new File(join(File.separator, zipPath, "test7.zip")));
//        when(request.getZipFile()).thenReturn(is);
//        validator.validateIfZipContainsJsonFile(tempFolder);
//
//        // when
//        validator.validateScriptsFormat(tempFolder);
//
//        // then
//        assertThat(response.getViolations()).hasSize(1);
//        boolean containsField = false;
//        for(Violation violation : response.getViolations()) {
//            containsField = violation.getMessage().contains(
//                "Deploy and undeploy script must be in .sql format"
//            );
//        }
//        assertThat(containsField).isTrue();
//    }
//
//    @Test
//    public void testValidateJsonFileStructureIfHasNotUndeployScriptField() throws IOException {
//        // given
//        InputStream is = new FileInputStream(new File(join(File.separator, zipPath, "test6.zip")));
//        when(request.getZipFile()).thenReturn(is);
//        validator.validateIfZipContainsJsonFile(tempFolder);
//
//        // when
//        validator.validateJsonFileStructure(tempFolder);
//
//        // then
//        assertThat(response.getViolations()).hasSize(1);
//        boolean containsField = false;
//        for(Violation violation : response.getViolations()) {
//            containsField = violation.getMessage().contains(
//                "Loaded JSON file does not have required fields."
//            );
//        }
//        assertThat(containsField).isTrue();
//    }
//
//    @Test
//    public void testValidateJsonFileStructureIfHasNotDeployScriptField() throws IOException {
//        // given
//        InputStream is = new FileInputStream(new File(join(File.separator, zipPath, "test5.zip")));
//        when(request.getZipFile()).thenReturn(is);
//        validator.validateIfZipContainsJsonFile(tempFolder);
//
//        // when
//        validator.validateJsonFileStructure(tempFolder);
//
//        // then
//        assertThat(response.getViolations()).hasSize(1);
//        boolean containsField = false;
//        for(Violation violation : response.getViolations()) {
//            containsField = violation.getMessage().contains(
//                "Loaded JSON file does not have required fields."
//            );
//        }
//        assertThat(containsField).isTrue();
//    }
//
//    @Test
//    public void testValidateJsonFileStructureIfHasNotStatusField() throws IOException {
//        // given
//        InputStream is = new FileInputStream(new File(join(File.separator, zipPath, "test4.zip")));
//        when(request.getZipFile()).thenReturn(is);
//        validator.validateIfZipContainsJsonFile(tempFolder);
//
//        // when
//        validator.validateJsonFileStructure(tempFolder);
//
//        // then
//        assertThat(response.getViolations()).hasSize(1);
//        boolean containsField = false;
//        for(Violation violation : response.getViolations()) {
//            containsField = violation.getMessage().contains(
//                "Loaded JSON file does not have required fields."
//            );
//        }
//        assertThat(containsField).isTrue();
//    }
//
//    @Test
//    public void testValidateJsonFileStructureIfHasNotServerIdField() throws IOException {
//        // given
//        InputStream is = new FileInputStream(new File(join(File.separator, zipPath, "test3.zip")));
//        when(request.getZipFile()).thenReturn(is);
//        validator.validateIfZipContainsJsonFile(tempFolder);
//
//        // when
//        validator.validateJsonFileStructure(tempFolder);
//
//        // then
//        assertThat(response.getViolations()).hasSize(1);
//        boolean containsField = false;
//        for(Violation violation : response.getViolations()) {
//            containsField = violation.getMessage().contains(
//                "Loaded JSON file does not have required fields."
//            );
//        }
//        assertThat(containsField).isTrue();
//    }
//
//    @Test
//    public void testValidateJsonFileStructureIfHasNotIsDefaultField() throws IOException {
//        // given
//        InputStream is = new FileInputStream(new File(join(File.separator, zipPath, "test2.zip")));
//        when(request.getZipFile()).thenReturn(is);
//        validator.validateIfZipContainsJsonFile(tempFolder);
//
//        // when
//        validator.validateJsonFileStructure(tempFolder);
//
//        // then
//        assertThat(response.getViolations()).hasSize(1);
//        boolean containsField = false;
//        for(Violation violation : response.getViolations()) {
//            containsField = violation.getMessage().contains(
//                "Loaded JSON file does not have required fields."
//            );
//        }
//        assertThat(containsField).isTrue();
//    }
//
//    @Test
//    public void testValidateJsonFileStructureIfHasNotNameField() throws IOException {
//        // given
//        InputStream is = new FileInputStream(new File(join(File.separator, zipPath, "test1.zip")));
//        when(request.getZipFile()).thenReturn(is);
//        validator.validateIfZipContainsJsonFile(tempFolder);
//
//        // when
//        validator.validateJsonFileStructure(tempFolder);
//
//        // then
//        assertThat(response.getViolations()).hasSize(1);
//        boolean containsField = false;
//        for(Violation violation : response.getViolations()) {
//            containsField = violation.getMessage().contains(
//                "Loaded JSON file does not have required fields."
//            );
//        }
//        assertThat(containsField).isTrue();
//    }
//
//    @Test
//    public void testValidateJsonFileStructureIfHasNotVersionField() throws IOException {
//        // given
//        InputStream is = new FileInputStream(new File(join(File.separator, zipPath, "test10.zip")));
//        when(request.getZipFile()).thenReturn(is);
//        validator.validateIfZipContainsJsonFile(tempFolder);
//
//        // when
//        validator.validateJsonFileStructure(tempFolder);
//
//        // then
//        assertThat(response.getViolations()).hasSize(1);
//        boolean containsField = false;
//        for(Violation violation : response.getViolations()) {
//            containsField = violation.getMessage().contains(
//                "Loaded JSON file does not have required fields."
//            );
//        }
//        assertThat(containsField).isTrue();
//    }
//
//    @Test
//    public void testValidateIfZipContainsJsonFile() throws IOException {
//        // given
//        String content = "application/rar";
//        when(request.getContentType()).thenReturn(content);
//        InputStream is = new FileInputStream(new File(join(File.separator, zipPath, "test1.zip")));
//        when(request.getZipFile()).thenReturn(is);
//
//        // when
//        validator.validateIfZipContainsJsonFile(tempFolder);
//
//        // then
//        for(Violation violation : response.getViolations()) {
//            assertThat(violation.getMessage()).doesNotContain(
//                "Loaded zip does not contains required JSON file."
//            );
//        }
//    }
//
//    @Test
//    public void testValidateWithViolations() throws IOException {
//        // given
//        String content = "application/rar";
//        when(request.getContentType()).thenReturn(content);
//        InputStream is = new FileInputStream(new File(join(File.separator, zipPath, "test9.zip")));
//        when(request.getZipFile()).thenReturn(is);
//        validator.validateIfZipContainsJsonFile(tempFolder);
//
//        // when
//        validator.validate(tempFolder);
//
//        // then
//        assertThat(response.getViolations()).hasSize(4);
//    }
//
//    @Test
//    public void testValidateIfZipContainsJsonFileWhenExOccurred() throws IOException {
//        // given
//        String content = "application/rar";
//        when(request.getContentType()).thenReturn(content);
//        when(request.getZipFile()).thenReturn(zipFile);
//        when(request.getZipFile()).thenReturn(null);
//
//        // then
//        expectedException.expect(EidIllegalStateException.class);
//        expectedException.expectMessage(containsString("20170605:113002"));
//
//        // when
//        validator.validateIfZipContainsJsonFile(tempFolder);
//    }
//
//    @Test
//    public void testValidateIfZipContainsJsonFileWhenErrorOccurred() throws IOException {
//        // given
//        String content = "application/rar";
//        when(request.getContentType()).thenReturn(content);
//        when(request.getZipFile()).thenReturn(zipFile);
//
//        // when
//        validator.validateIfZipContainsJsonFile(tempFolder);
//
//        // then
//        assertThat(response.getViolations()).hasSize(1);
//    }
//
//    @Test
//    public void testValidateJsonFileStructureIfHasFields() throws IOException {
//        // given
//        InputStream is = new FileInputStream(new File(join(File.separator, zipPath, "test13.zip")));
//        when(request.getZipFile()).thenReturn(is);
//        validator.validateIfZipContainsJsonFile(tempFolder);
//        String id = "template";
//        String serverId = "3050";
//        String status = "created";
//        String version = "345435.0.3";
//
//        // when
//        validator.validateJsonFileStructure(tempFolder);
//
//        // then
//        assertThat(response.getViolations()).hasSize(0);
//        assertThat(validator.getId()).isEqualTo(id);
//        assertThat(validator.getServerId()).isEqualTo(serverId);
//        assertThat(validator.getStatus()).isEqualTo(status);
//        assertThat(validator.getVersion()).isEqualTo(version);
//        assertThat(validator.isDefault()).isTrue();
//    }
//
//    @Test
//    public void testValidateScriptsFormat() throws IOException {
//        // given
//        InputStream is = new FileInputStream(new File(join(File.separator, zipPath, "test13.zip")));
//        when(request.getZipFile()).thenReturn(is);
//        validator.validateIfZipContainsJsonFile(tempFolder);
//
//        // when
//        validator.validateScriptsFormat(tempFolder);
//
//        // then
//        assertThat(response.getViolations()).hasSize(0);
//    }
//
//    @Test
//    public void testValidateIfScriptsExist() throws IOException {
//        // given
//        InputStream is = new FileInputStream(new File(join(File.separator, zipPath, "test13.zip")));
//        when(request.getZipFile()).thenReturn(is);
//        validator.validateIfZipContainsJsonFile(tempFolder);
//
//        // when
//        validator.validateIfScriptsExist(tempFolder);
//
//        // then
//        assertThat(response.getViolations()).hasSize(0);
//    }
//
//    @Test
//    public void testValidateZipWhenContentIsNotZip() {
//        // given
//        String content = "application/rar";
//        when(request.getContentType()).thenReturn(content);
//
//        // when
//        validator.validateZip();
//
//        // then
//        assertThat(response.getViolations()).hasSize(1);
//    }
//
//    @Test
//    public void testValidateZipWhenContentIsZip() {
//        // given
//        String content = "application/zip";
//        when(request.getContentType()).thenReturn(content);
//
//        // when
//        validator.validateZip();
//
//        // then
//        assertThat(response.getViolations()).hasSize(0);
//    }
//
//    @Test
//    public void testGetId() throws Exception {
//        // when
//        String actual = validator.getId();
//
//        // then
//        assertThat(actual).isNotNull();
//        assertThat(actual).isEqualTo(id);
//    }
//
//    @Test
//    public void testIsDefault() throws Exception {
//        // when
//        boolean actual = validator.isDefault();
//
//        // then
//        assertThat(actual).isTrue();
//    }
//
//    @Test
//    public void testGetStatus() throws Exception {
//        // when
//        String actual = validator.getStatus();
//
//        // then
//        assertThat(actual).isNotNull();
//        assertThat(actual).isEqualTo(status);
//    }
//
//    @Test
//    public void testGetServerId() throws Exception {
//        // when
//        String actual = validator.getServerId();
//
//        // then
//        assertThat(actual).isNotNull();
//        assertThat(actual).isEqualTo(serverId);
//    }
//
//    @Test
//    public void testGetVersion() throws Exception {
//        // when
//        String actual = validator.getVersion();
//
//        // then
//        assertThat(actual).isNotNull();
//        assertThat(actual).isEqualTo(version);
//    }
//
//    @Test
//    public void testBuilder() throws Exception {
//        // when
//        Validator validatorBuilder = Validator.builder()
//            .id(id)
//            .isDefault(true)
//            .jsonFilename(jsonName)
//            .request(request)
//            .response(response)
//            .serverId(serverId)
//            .status(status)
//            .version(version)
//            .build();
//
//        // then
//        assertThat(validatorBuilder).isNotNull();
//    }
//
//    private static final class ResponseImpl implements Response {
//
//        @Getter
//        private final Collection<Violation> violations = new HashSet<>();
//        @Getter
//        private String id;
//        @Getter
//        private String status;
//        @Getter
//        private boolean isDefault;
//        @Getter
//        private String serverId;
//        @Getter
//        private String version;
//
//        @Override
//        public void addViolation(Violation violation) {
//            violations.add(violation);
//        }
//
//        @Override
//        public boolean isSuccessful() {
//            return violations.isEmpty();
//        }
//
//        @Override
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        @Override
//        public void setStatus(String status) {
//            this.status = status;
//        }
//
//        @Override
//        public void setDefault(boolean isDefault) {
//            this.isDefault = isDefault;
//        }
//
//        @Override
//        public void setServerId(String versionId) {
//            this.serverId = serverId;
//        }
//
//        @Override
//        public void setVersion(String version) {
//            this.version = version;
//        }
//    }
//
//}
