package pl.gov.coi.cascades.server.domain.launchdatabase;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 07.03.17.
 */
public class UsernameAndPasswordCredentialsGeneratorServiceTest {

    @Test
    public void testGenerate() throws Exception {
        // given
        UsernameAndPasswordCredentialsGeneratorService credentials = new UsernameAndPasswordCredentialsGeneratorService();

        // when
        UsernameAndPasswordCredentials actual = credentials.generate();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getUsername()).hasSize(8);
        assertThat(actual.getPassword()).hasSize(24);
    }

    @Test
    public void shouldBeFirstLetterInUsername() {
        // given
        UsernameAndPasswordCredentialsGeneratorService credentials = new UsernameAndPasswordCredentialsGeneratorService();

        // when
        UsernameAndPasswordCredentials actual = credentials.generate();

        // then
        assertFalse(StringUtils.isNumeric(actual.getUsername().substring(0, 1)));
    }

}
