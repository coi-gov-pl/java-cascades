//package pl.gov.coi.cascades.server.domain.loadtemplate;
//
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnit;
//import org.mockito.junit.MockitoRule;
//import java.io.InputStream;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
///**
// * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
// * @since 14.06.17.
// */
//public class RequestTest {
//
//    private String name = "oracle_template";
//    private Long size = 43243L;
//    private boolean isEmpty = false;
//    private String contentType = "application/zip";
//
//    @Mock
//    private InputStream zipFile;
//
//    @Rule
//    public MockitoRule mockitoRule = MockitoJUnit.rule();
//
//    @Rule
//    public ExpectedException expectedException = ExpectedException.none();
//
//    @Test
//    public void testDefaultConstructor() throws Exception {
//        // when
//        Request actual = new Request(
//            zipFile,
//            name,
//            size,
//            isEmpty,
//            contentType
//        );
//
//        // then
//        assertThat(actual).isNotNull();
//    }
//
//    @Test
//    public void testGetZipFile() throws Exception {
//        // given
//        Request request = new Request(
//            zipFile,
//            name,
//            size,
//            isEmpty,
//            contentType
//        );
//
//        // when
//        InputStream actual = request.getZipFile();
//
//        // then
//        assertThat(actual).isNotNull();
//    }
//
//    @Test
//    public void testGetName() throws Exception {
//        // given
//        Request request = new Request(
//            zipFile,
//            name,
//            size,
//            isEmpty,
//            contentType
//        );
//
//        // when
//        String actual = request.getName();
//
//        // then
//        assertThat(actual).isNotNull();
//    }
//
//    @Test
//    public void testGetSize() throws Exception {
//        // given
//        Request request = new Request(
//            zipFile,
//            name,
//            size,
//            isEmpty,
//            contentType
//        );
//
//        // when
//        Long actual = request.getSize();
//
//        // then
//        assertThat(actual).isNotNull();
//    }
//
//    @Test
//    public void testIsEmpty() throws Exception {
//        // given
//        Request request = new Request(
//            zipFile,
//            name,
//            size,
//            isEmpty,
//            contentType
//        );
//
//        // when
//        boolean actual = request.isEmpty();
//
//        // then
//        assertThat(actual).isNotNull();
//    }
//
//    @Test
//    public void testGetContentType() throws Exception {
//        // given
//        Request request = new Request(
//            zipFile,
//            name,
//            size,
//            isEmpty,
//            contentType
//        );
//
//        // when
//        String actual = request.getContentType();
//
//        // then
//        assertThat(actual).isNotNull();
//    }
//
//    @Test
//    public void testBuilder() throws Exception {
//        // when
//        Request requestBuilder = Request.builder()
//            .contentType(contentType)
//            .isEmpty(isEmpty)
//            .name(name)
//            .size(size)
//            .zipFile(zipFile)
//            .build();
//
//        // then
//        assertThat(requestBuilder).isNotNull();
//        assertThat(requestBuilder.getContentType()).isEqualTo(contentType);
//        assertThat(requestBuilder.isEmpty()).isFalse();
//        assertThat(requestBuilder.getSize()).isEqualTo(size);
//        assertThat(requestBuilder.getZipFile()).isEqualTo(zipFile);
//    }
//
//}
