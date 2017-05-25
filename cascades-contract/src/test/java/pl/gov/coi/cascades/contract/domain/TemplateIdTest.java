package pl.gov.coi.cascades.contract.domain;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 21.02.17.
 */
public class TemplateIdTest {

    private String id;
    private String serverId;
    private String version;

    @Before
    public void setUp() {
        id = "1234567abcd";
        serverId = "3v3454v4";
        version = "0.0.1";
    }

    @Test
    public void testDefaultConstructor() throws Exception {
        // when
        TemplateId actual = new TemplateId(
            id,
            TemplateIdStatus.CREATED,
            true,
            serverId,
            version
        );

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getStatus()).isEqualTo(TemplateIdStatus.CREATED);
        assertThat(actual.getServerId()).isEqualTo(serverId);
        assertThat(actual.getVersion()).isEqualTo(version);
        assertThat(actual.isDefault()).isTrue();
    }

}
