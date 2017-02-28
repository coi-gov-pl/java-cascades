package pl.gov.coi.cascades.server.domain;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.02.17.
 */
public interface DatabaseLimitGateway {

    /**
     * Method checks if given number of databases does not exceed the personal limit.
     *
     * @param user Given user that will be checked.
     * @return Answer false, if given number of databases does not exceed personal the limit.
     */
    boolean isPersonalLimitExceeded(User user);

    int getPersonalLimitPerUser(User user);

    boolean isGlobalLimitExceeded();

    int getGlobalLimit();

}
