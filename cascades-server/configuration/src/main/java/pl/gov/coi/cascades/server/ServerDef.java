package pl.gov.coi.cascades.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.07.17
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ServerDef {
    private String serverId;
    private String type;
    private String dbname;
    private String user;
    private String password;
    private String host;
    private int port;
}
