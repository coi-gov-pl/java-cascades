package pl.gov.coi.cascades.supplier.uri;

import org.junit.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 30.03.17
 */
public class SchemeHostPortUriTest {
    @Test
    public void testGet() {
        // given
        SchemeHostPortUri schemeHostPortUri = new SchemeHostPortUri(
            "https",
            "example.org",
            1234
        );

        // when
        URI uri = schemeHostPortUri.get();

        // then
        assertThat(uri).isNotNull()
            .hasToString("https://example.org:1234");
    }

    @Test
    public void testGetWithPath() {
        // given
        SchemeHostPortUri schemeHostPortUri = new SchemeHostPortUri(
            "https",
            "example.org",
            443
        );
        schemeHostPortUri.setPath("/databases");

        // when
        URI uri = schemeHostPortUri.get();

        // then
        assertThat(uri).isNotNull()
            .hasToString("https://example.org/databases");
    }

}
