package pl.gov.coi.cascades.server.persistance.stub;

import lombok.AllArgsConstructor;
import lombok.Builder;
import pl.gov.coi.cascades.contract.domain.NetworkBind;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 06.03.17.
 */
@Builder
@AllArgsConstructor
public class NetworkBindStub implements NetworkBind {

    private int port;
    private String host;

    @Override
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
