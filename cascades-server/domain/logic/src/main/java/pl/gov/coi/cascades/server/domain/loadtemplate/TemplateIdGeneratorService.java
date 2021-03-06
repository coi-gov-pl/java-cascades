package pl.gov.coi.cascades.server.domain.loadtemplate;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 29.06.17
 */
public class TemplateIdGeneratorService {

    private static final int TEMPLATE_ID_LENGTH = 24;
    private static final String CHAR_ARRAY =
        "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_";
    private Random rand;

    /**
     * Default constructor.
     */
    public TemplateIdGeneratorService() {
        rand = new SecureRandom();
    }

    String generateTemplateId() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < TEMPLATE_ID_LENGTH; i++) {
            stringBuilder.append(CHAR_ARRAY.charAt(rand.nextInt(CHAR_ARRAY.length())));
        }
        return stringBuilder.toString();
    }

}
