package pl.gov.coi.cascades.server.domain;

import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;

import java.util.Random;
import java.util.UUID;

public class UsernameAndPasswordCredentialsGeneratorService {

    private static final int BASE = 36;
    public static final int PASSWORD_LENGTH = 8;

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
        UUID uniqueKey = UUID.randomUUID();
        String uuid = uniqueKey.toString().replace("-","");
	    return uuid.substring(0, PASSWORD_LENGTH);
    }

    private char[] generatePassword() {
        Random rand = new Random();
        Integer pass = rand.nextInt();
        return Integer.toString(pass, BASE).toCharArray();
    }

}
