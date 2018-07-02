package pl.gov.coi.cascades.server;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.gov.coi.cascades.contract.domain.NetworkBind;

@AllArgsConstructor
@Getter
@Setter
public class NetworkBindImpl implements NetworkBind {

    private String host;
    private int port;
}
