package pl.gov.coi.cascades.server.domain.launchdatabase;

import com.google.common.annotations.VisibleForTesting;
import pl.gov.coi.cascades.contract.domain.DatabaseId;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 03.04.17.
 */
public class DatabaseIdGeneratorService {
    private static final Random DEFUALT_RND = new SecureRandom();
    private static final int RADIX_36 = 36;

    private final Random random;

    public DatabaseIdGeneratorService() {
        this(DEFUALT_RND);
    }

    @VisibleForTesting
    DatabaseIdGeneratorService(Random random) {
        this.random = random;
    }

    DatabaseId generate() {
        // unsigned long
        long longId = random.nextLong() >>> 1;
        return new DatabaseId(
            Long.toString(longId, RADIX_36)
        );
    }
}
