package pl.gov.coi.cascades.server.presentation.launchdatabase;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.contract.domain.Template;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.04.17.
 */
public class DefaultOptionalMapperTest {

    @Mock
    private Template template;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testToJava8() throws Exception {
        // given
        com.google.common.base.Optional<Template> guavaOptional = com.google.common.base.Optional.of(template);
        DefaultOptionalMapper mapper = new DefaultOptionalMapper();

        // when
        Optional<Template> actual = mapper.toJava8(guavaOptional);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isExactlyInstanceOf(Optional.class);
    }

}
