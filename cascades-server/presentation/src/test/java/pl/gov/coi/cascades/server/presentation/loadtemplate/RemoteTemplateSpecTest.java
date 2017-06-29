package pl.gov.coi.cascades.server.presentation.loadtemplate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 14.06.17.
 */
public class RemoteTemplateSpecTest {

    private static final String id = "dj5ujs749";
    private static final String name = "oracle_template";
    private static final String status = "created";
    private static final boolean isDefault = false;
    private static final String version = "1.0.1";
    private static final String serverId = "2020";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testDefaultConstructor() {
        // when
        RemoteTemplateSpec actual = new RemoteTemplateSpec(
            id,
            name,
            status,
            isDefault,
            serverId,
            version
        );

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testGetId() throws Exception {
        // given
        RemoteTemplateSpec remoteTemplateSpec = new RemoteTemplateSpec(
            id,
            name,
            status,
            isDefault,
            serverId,
            version
        );

        // when
        String actual = remoteTemplateSpec.getId();

        // then
        assertThat(actual).isEqualTo(id);
    }

    @Test
    public void testGetStatus() throws Exception {
        // given
        RemoteTemplateSpec remoteTemplateSpec = new RemoteTemplateSpec(
            id,
            name,
            status,
            isDefault,
            serverId,
            version
        );

        // when
        String actual = remoteTemplateSpec.getStatus();

        // then
        assertThat(actual).isEqualTo(status);
    }

    @Test
    public void testIsDefault() throws Exception {
        // given
        RemoteTemplateSpec remoteTemplateSpec = new RemoteTemplateSpec(
            id,
            name,
            status,
            isDefault,
            serverId,
            version
        );

        // when
        boolean actual = remoteTemplateSpec.isDefault();

        // then
        assertThat(actual).isEqualTo(isDefault);
    }

    @Test
    public void testGetServerId() throws Exception {
        // given
        RemoteTemplateSpec remoteTemplateSpec = new RemoteTemplateSpec(
            id,
            name,
            status,
            isDefault,
            serverId,
            version
        );

        // when
        String actual = remoteTemplateSpec.getServerId();

        // then
        assertThat(actual).isEqualTo(serverId);
    }

    @Test
    public void testGetVersion() throws Exception {
        // given
        RemoteTemplateSpec remoteTemplateSpec = new RemoteTemplateSpec(
            id,
            name,
            status,
            isDefault,
            serverId,
            version
        );

        // when
        String actual = remoteTemplateSpec.getVersion();

        // then
        assertThat(actual).isEqualTo(version);
    }

}
