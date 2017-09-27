package pl.gov.coi.cascades.server;

import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 04.08.17
 */
public class ConnectionConfigurator {

    ConnectionConfiguration getConnectionConfiguration(ServerDef serverDef) {
        String driver;
        String url;
        switch (serverDef.getType()) {
            case "ora12c":
                driver = "oracle.jdbc.driver.OracleDriver";
                url = "jdbc:oracle:thin:@//%s:%d/%s";
                break;
            case "pgsql":
                driver = "org.postgresql.Driver";
                url = "jdbc:postgresql://%s:%d/%s";
                break;
            default:
                throw new EidIllegalArgumentException(
                    "20170728:150904",
                    "Given driver hasn't been recognised."
                );
        }
        return new ConnectionConfiguration(
            driver,
            url
        );
    }

}
