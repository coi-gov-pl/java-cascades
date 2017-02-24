package pl.gov.coi.cascades.server.presentation;

import pl.gov.coi.cascades.server.domain.model.User;

public interface UserSession {

    /**
     * Method gives signed in user.
     *
     * @return Signed in user.
     */
    User getSignedInUser();

}
