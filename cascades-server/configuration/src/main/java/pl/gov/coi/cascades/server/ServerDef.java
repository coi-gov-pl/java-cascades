package pl.gov.coi.cascades.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.07.17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerDef {
    private String serverId;
    private String type;
    private String dbname;
    private String user;
    private String password;
    private String host;
    private int port;

    /**
     * Get new {@link ServerDef} instance with the same data expect database name.
     * @param newDatabaseName new database name
     * @return new instance with new database name
     */
    public ServerDef getWithNewDatabaseName(String newDatabaseName) {
        return new ServerDef(
            this.serverId,
            this.type,
            newDatabaseName,
            this.user,
            this.password,
            this.host,
            this.port
            );
    }
}
