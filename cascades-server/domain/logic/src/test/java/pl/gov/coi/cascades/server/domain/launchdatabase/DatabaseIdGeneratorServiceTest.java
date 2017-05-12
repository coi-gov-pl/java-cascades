package pl.gov.coi.cascades.server.domain.launchdatabase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.server.domain.DatabaseIdMapper;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 03.04.17.
 */
public class DatabaseIdGeneratorServiceTest {

    private static final int RADIX_36 = 36;

    @Mock
    private Random random;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testGenerate() throws Exception {
        // given
        DatabaseIdMapper databaseIdMapper = new DatabaseIdMapper();
        DatabaseIdGeneratorService databaseIdGeneratorService = new DatabaseIdGeneratorService(
            random,
            databaseIdMapper
        );
        Long generateId = 2144123525L;
        Long shiftedId = generateId >>> 1;
        when(random.nextLong()).thenReturn(generateId);

        // when
        DatabaseId actual = databaseIdGeneratorService.generate();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(Long.toString(shiftedId, RADIX_36));
    }

    @Test
    public void testDefaultConstructor() {
        // when
        DatabaseIdGeneratorService actual = new DatabaseIdGeneratorService();

        // then
        assertThat(actual).isNotNull();
    }

}
