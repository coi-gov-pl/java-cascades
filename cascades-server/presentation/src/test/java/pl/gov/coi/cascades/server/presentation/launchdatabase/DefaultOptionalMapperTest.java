package pl.gov.coi.cascades.server.presentation.launchdatabase;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.TemplateId;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.04.17.
 */
public class DefaultOptionalMapperTest {

    @Mock
    private TemplateId templateId;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testToJava8() throws Exception {
        // given
        com.google.common.base.Optional<TemplateId> guavaOptional = com.google.common.base.Optional.of(templateId);
        DefaultOptionalMapper mapper = new DefaultOptionalMapper();

        // when
        Optional<TemplateId> actual = mapper.toJava8(guavaOptional);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).isExactlyInstanceOf(Optional.class);
    }

}
