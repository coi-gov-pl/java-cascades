package pl.gov.coi.cascades.server.persistance.stub;

import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 10.03.17.
 */
public class UsernameAndPasswordCredentialsStub implements UsernameAndPasswordCredentials {

    private final String name;

    public UsernameAndPasswordCredentialsStub(String name) {
        this.name = name;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public char[] getPassword() {
        return new char[0];
    }

}
