package pl.gov.coi.cascades.server;

import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 04.08.17
 */
public class ConnectionConfigurator {

    ConnectionConfiguration getConnectionConfiguration(String databaseType) {
        String driverClass;
        String jdbdUrlTemplate;
        switch (databaseType) {
            case "ora12c":
                driverClass = "oracle.jdbc.driverClass.OracleDriver";
                jdbdUrlTemplate = "jdbc:oracle:thin:@//%s:%d/%s";
                break;
            case "pgsql":
                driverClass = "org.postgresql.Driver";
                jdbdUrlTemplate = "jdbc:postgresql://%s:%d/%s";
                break;
            default:
                throw new EidIllegalArgumentException(
                    "20170728:150904",
                    String.format("Given database type '%s' hasn't been recognised.", databaseType)
                );
        }
        return new ConnectionConfiguration(
            driverClass,
            jdbdUrlTemplate
        );
    }

}
