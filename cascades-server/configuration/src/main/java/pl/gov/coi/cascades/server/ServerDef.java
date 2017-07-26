package pl.gov.coi.cascades.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.07.17
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServerDef {
    private String serverId;
    private String dbname;
    private String user;
    private String password;
    private String host;
    private int port;
}
