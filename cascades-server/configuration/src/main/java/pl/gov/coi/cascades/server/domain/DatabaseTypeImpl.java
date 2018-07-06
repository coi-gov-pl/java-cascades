package pl.gov.coi.cascades.server.domain;

import lombok.AllArgsConstructor;
import pl.gov.coi.cascades.contract.domain.ConnectionStringProducer;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.contract.domain.NetworkBind;
import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;

@AllArgsConstructor
public class DatabaseTypeImpl implements DatabaseType {

    private static final String ORACLE_DATABASE = "jdbc:oracle:thin:@//%s:%d/%s";
    private static final String POSTGRES_DATABASE = "jdbc:postgresql://%s:%d/%s";
    private static final String PGPSQL = "pgpsql";
    private static final String ORA12C = "ora12c";

    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ConnectionStringProducer getConnectionStringProducer() {
        if (name.equalsIgnoreCase(PGPSQL)) {
            createProduce(POSTGRES_DATABASE);

        } else if (name.equalsIgnoreCase(ORA12C)) {
            createProduce(ORACLE_DATABASE);
        }

        throw new EidIllegalArgumentException(
            "20180706:154716",
            String.format("Given database type '%s' hasn't been recognised.", name)
        );
    }

    private ConnectionStringProducer createProduce(String databaseType) {
        new ConnectionStringProducer() {
            @Override
            public String produce(NetworkBind bind, String databaseName) {
                return String.format(
                    databaseType,
                    bind.getHost(),
                    bind.getPort(),
                    databaseName
                );
            }
        };

        throw new EidIllegalStateException(
            "20180706:154616",
            "Can't create connection string producer"
        );
    }
}


