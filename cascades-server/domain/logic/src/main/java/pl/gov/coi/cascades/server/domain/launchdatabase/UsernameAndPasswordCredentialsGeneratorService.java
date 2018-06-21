package pl.gov.coi.cascades.server.domain.launchdatabase;

import org.apache.commons.lang.RandomStringUtils;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;

import java.util.Random;

public class UsernameAndPasswordCredentialsGeneratorService {

    private static final int PASSWORD_LENGTH = 24;
    private static final int USERNAME_LENGTH = 10;
    private static final String CHAR_PASSWORD =
        "0123456789" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" +
            "!@#$%^&*";
    private Random randomGenerator;

    /**
     * Default constructor.
     * @param randomGenerator random generator implementation
     */
    public UsernameAndPasswordCredentialsGeneratorService(Random randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    /**
     * Method gives id of database for given name of database instance.
     *
     * @return Username an password for user.
     */
    public UsernameAndPasswordCredentials generate() {
        String username = generateUsername();
        char[] password = generateCharArrayPassword();
        return new UsernameAndPasswordCredentialsImpl(username, password);
    }

    private static String generateUsername() {
        return RandomStringUtils.randomAlphabetic(USERNAME_LENGTH);
    }

    private char[] generateCharArrayPassword() {
        char[] password = new char[PASSWORD_LENGTH];
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            char c = CHAR_PASSWORD.charAt(randomGenerator.nextInt(CHAR_PASSWORD.length()));
            password[i] = c;
        }
        return password;
    }

}
