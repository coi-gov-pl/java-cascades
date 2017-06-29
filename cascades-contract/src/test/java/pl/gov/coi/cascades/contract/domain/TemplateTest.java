package pl.gov.coi.cascades.contract.domain;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 21.02.17.
 */
public class TemplateTest {

    private String id;
    private String name;
    private String serverId;
    private String version;
    private TemplateIdStatus status;
    private boolean isDefault;

    @Before
    public void setUp() {
        id = "oracle_template";
        name = "hbet6f73";
        serverId = "1234";
        version = "0.0.1";
        status = TemplateIdStatus.CREATED;
        isDefault = true;
    }

    @Test
    public void testToString() {
        // given
        Template templateBuilder = Template.builder()
            .id(id)
            .name(name)
            .isDefault(isDefault)
            .serverId(serverId)
            .status(status)
            .version(version)
            .build();

        // when
        String actual = templateBuilder.toString();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).contains(
            String.valueOf(isDefault),
            serverId,
            status.toString(),
            version,
            id,
            name
        );
    }

    @Test
    public void testBuilder() {
        // when
        Template templateBuilder = Template.builder()
            .id(id)
            .name(name)
            .isDefault(true)
            .serverId(serverId)
            .status(status)
            .version(version)
            .build();

        // then
        assertThat(templateBuilder).isNotNull();
        assertThat(templateBuilder.getId()).isNotNull();
        assertThat(templateBuilder.getId()).isEqualTo(id);
        assertThat(templateBuilder.getServerId()).isNotNull();
        assertThat(templateBuilder.getServerId()).isEqualTo(serverId);
        assertThat(templateBuilder.getStatus()).isNotNull();
        assertThat(templateBuilder.getStatus()).isEqualTo(status);
        assertThat(templateBuilder.getVersion()).isNotNull();
        assertThat(templateBuilder.getVersion()).isEqualTo(version);
        assertThat(templateBuilder.getName()).isNotNull();
        assertThat(templateBuilder.getName()).isEqualTo(name);
    }

    @Test
    public void testDefaultConstructor() throws Exception {
        // when
        Template actual = new Template(
            id,
            name,
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
        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.getVersion()).isEqualTo(version);
        assertThat(actual.isDefault()).isTrue();
    }

}
