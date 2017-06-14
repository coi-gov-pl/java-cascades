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
    private TemplateIdStatus status;
    private boolean isDefault;

    @Before
    public void setUp() {
        id = "oracle_template";
        serverId = "1234";
        version = "0.0.1";
        status = TemplateIdStatus.CREATED;
        isDefault = true;
    }

    @Test
    public void testToString() {
        // given
        TemplateId templateIdBuilder = TemplateId.builder()
            .id(id)
            .isDefault(isDefault)
            .serverId(serverId)
            .status(status)
            .version(version)
            .build();

        // when
        String actual = templateIdBuilder.toString();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).contains(
            String.valueOf(isDefault),
            serverId,
            status.toString(),
            version,
            id
        );
    }

    @Test
    public void testBuilder() {
        // when
        TemplateId templateIdBuilder = TemplateId.builder()
            .id(id)
            .isDefault(true)
            .serverId(serverId)
            .status(status)
            .version(version)
            .build();

        // then
        assertThat(templateIdBuilder).isNotNull();
        assertThat(templateIdBuilder.getId()).isNotNull();
        assertThat(templateIdBuilder.getId()).isEqualTo(id);
        assertThat(templateIdBuilder.getServerId()).isNotNull();
        assertThat(templateIdBuilder.getServerId()).isEqualTo(serverId);
        assertThat(templateIdBuilder.getStatus()).isNotNull();
        assertThat(templateIdBuilder.getStatus()).isEqualTo(status);
        assertThat(templateIdBuilder.getVersion()).isNotNull();
        assertThat(templateIdBuilder.getVersion()).isEqualTo(version);
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
