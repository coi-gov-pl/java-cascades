package pl.gov.coi.cascades.server.domain;

import com.github.slugify.Slugify;
import pl.gov.coi.cascades.contract.domain.DatabaseId;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * Class for generating id for databases.
 */
public class DatabaseIdGeneratorService {

    public static final int DATABASE_NAME_LENGTH = 8;
    private static final String VALUES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz#$_";

    /**
     * Method gives id of database for given name of database instance.
     *
     * @param instanceName Given name of database instance.
     * @return Id of database.
     */
    public DatabaseId generate(String instanceName) {
        Slugify slg = new Slugify();
        String uniqueKey = UUID.randomUUID().toString();
        String result = slg.slugify(instanceName);
        return new DatabaseId(result.concat(uniqueKey));
    }

    /**
     * Method gives random id of database.
     *
     * @return Id of database.
     */
    public DatabaseId generate() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder(DATABASE_NAME_LENGTH);
        for(int i = 0; i < DATABASE_NAME_LENGTH; i++) {
            stringBuilder.append(VALUES.charAt(secureRandom.nextInt(VALUES.length())));
        }
        return new DatabaseId(stringBuilder.toString());
    }

}
