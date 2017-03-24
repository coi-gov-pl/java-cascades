package pl.gov.coi.cascades.server.domain;

import java.util.Optional;

public interface UserGateway {

    /**
     * Method finds an optional of user for given user.
     *
     * @param userName Given name of user.
     * @return An optional of user.
     */
    Optional<User> find(String userName);

    /**
     * Method saves given user.
     *
     * @param user Given user to save.
     */
    void save(User user);

}
