package pl.gov.coi.cascades.server.persistance.hibernate.development;

import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.03.17.
 */
class DatabaseInstanceData {

    private final Map<Instance, DatabaseInstance> instances =
        new EnumMap<>(Instance.class);

    DatabaseInstanceData() {
        DatabaseInstance db = new DatabaseInstance();

        instances.put(Instance.ORA12E34, db);
    }

    DatabaseInstance get(Instance instance) {
        return instances.get(instance);
    }

    enum Instance {
        ORA12E34
    }

}
