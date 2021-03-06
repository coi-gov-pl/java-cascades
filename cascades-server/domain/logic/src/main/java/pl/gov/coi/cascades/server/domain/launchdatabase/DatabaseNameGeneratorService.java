package pl.gov.coi.cascades.server.domain.launchdatabase;

import com.github.slugify.Slugify;

import java.security.SecureRandom;

/**
 * Class for generating id for databases.
 */
public class DatabaseNameGeneratorService {

    private static final int DATABASE_NAME_LENGTH = 8;
    private static final String VALUES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_";
    private static final int CHARS_FROM_INSTANCE_NAME = 3;
    private SecureRandom secureRandom;

    public DatabaseNameGeneratorService() {
        secureRandom = new SecureRandom();
    }

    /**
     * Method gives id of database for given name of database instance.
     *
     * @param instanceName Given name of database instance.
     * @return Id of database.
     */
    String generate(String instanceName) {
        int chars = 0;
        Slugify slg = new Slugify();
        String result = slg.slugify(instanceName);
        StringBuilder stringBuilder = new StringBuilder(DATABASE_NAME_LENGTH);

        if (result.length() < CHARS_FROM_INSTANCE_NAME) {
            for(int i = 0; i < result.length(); i++) {
                stringBuilder.append(result.charAt(i));
                chars++;
            }
        } else {
            for(int i = 0; i < CHARS_FROM_INSTANCE_NAME; i++) {
                stringBuilder.append(result.charAt(i));
                chars++;
            }
        }

        for(int i = 0; i < DATABASE_NAME_LENGTH - chars; i++) {
            stringBuilder.append(VALUES.charAt(secureRandom.nextInt(VALUES.length())));
        }
        return stringBuilder.toString();
    }

    /**
     * Method gives random id of database.
     *
     * @return Id of database.
     */
    String generate() {
        return generate("");
    }

}
