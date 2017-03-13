package pl.gov.coi.cascades.server.domain.launchdatabase;

import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;

import java.util.Random;
import java.util.UUID;

public class UsernameAndPasswordCredentialsGeneratorService {

    private static final int PASSWORD_LENGTH = 24;
    private static final int USERNAME_LENGTH = 8;
    private static final String CHAR_PASSWORD =
        "0123456789" +
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
        "abcdefghijklmnopqrstuvwxyz" +
        "!@#$%^&*";
    private Random rand;

    /**
     * Default constructor.
     */
    public UsernameAndPasswordCredentialsGeneratorService() {
        rand = new Random();
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

	private String generateUsername() {
        String uuid = UUID.randomUUID().toString();
	    return uuid.substring(0, USERNAME_LENGTH);
    }

    private char[] generatePassword() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < PASSWORD_LENGTH; i++) {
            char c = (char)(rand.nextInt(CHAR_PASSWORD.length()));
            stringBuilder.append(c);
        }
        return stringBuilder.toString().toCharArray();
    }

}
