package pl.gov.coi.cascades.server.domain.launchdatabase;

import org.apache.commons.lang3.RandomStringUtils;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

public class UsernameAndPasswordCredentialsGeneratorService {

    private static final int PASSWORD_LENGTH = 24;
    private static final int USERNAME_LENGTH_WITHOUT_FIRST_LETTER = 7;
    private static final String CHAR_PASSWORD =
        "0123456789" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" +
            "!@#$%^&*";
    private static final int COUNT_LETTER = 1;
    private Random rand;

    /**
     * Default constructor.
     */
    public UsernameAndPasswordCredentialsGeneratorService() {
        rand = new SecureRandom();
    }

    /**
     * Method gives id of database for given name of database instance.
     *
     * @return Username an password for user.
     */
    public UsernameAndPasswordCredentials generate() {
        String username = generateUsername();
        char[] password = generatePassword();
        return new UsernameAndPasswordCredentialsImpl(username, password);
    }

    private static String generateUsername() {
        String uuid = UUID.randomUUID().toString();
        String randomLetter = RandomStringUtils.randomAlphabetic(COUNT_LETTER);
        return String.format("%s%s",
            randomLetter,
            uuid.substring(0, USERNAME_LENGTH_WITHOUT_FIRST_LETTER)
        );
    }

    private char[] generatePassword() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            char c = (char) (rand.nextInt(CHAR_PASSWORD.length()));
            stringBuilder.append(c);
        }
        return stringBuilder.toString().toCharArray();
    }
}
