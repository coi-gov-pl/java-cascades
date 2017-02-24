package pl.gov.coi.cascades.server.domain;

import com.google.common.base.Optional;

public interface UserGateway {

    /**
     * Method finds an optional of user for given user.
     *
     * @param user Given name of user.
     * @return An optional of user.
     */
    Optional<User> find(String user);

    /**
     * Method saves given user.
     *
     * @param user Given user to save.
     */
    void save(User user);

}
