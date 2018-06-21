package pl.gov.coi.cascades.server.domain.launchdatabase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;

import java.security.SecureRandom;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 07.03.17.
 */
public class UsernameAndPasswordCredentialsGeneratorServiceTest {

    private static final int PASSWORD_LENGTH = 24;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private Random randomGeneratorMock;

    @Test
    public void testGenerateRandomCredentials() {
        // given
        UsernameAndPasswordCredentialsGeneratorService credentials = new UsernameAndPasswordCredentialsGeneratorService(new SecureRandom());

        // when
        UsernameAndPasswordCredentials firstGeneration = credentials.generate();
        UsernameAndPasswordCredentials secondGeneration = credentials.generate();

        // then
        checkGeneratedDataIsRandom(firstGeneration, secondGeneration);
    }

    @Test
    public void testUsePassedGenerator() {
        //given
        UsernameAndPasswordCredentialsGeneratorService credentials = new UsernameAndPasswordCredentialsGeneratorService(randomGeneratorMock);

        //when
        credentials.generate();

        // then
        verify(randomGeneratorMock, times(PASSWORD_LENGTH)).nextInt(any(Integer.class));
    }

    private void checkGeneratedDataIsRandom(UsernameAndPasswordCredentials firstGeneration, UsernameAndPasswordCredentials secondGeneration) {
        assertThat(firstGeneration.getUsername()).isNotEqualTo(secondGeneration.getUsername());
        assertThat(firstGeneration.getPassword()).isNotEqualTo(secondGeneration.getPassword());
    }

}
