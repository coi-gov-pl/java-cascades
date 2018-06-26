package pl.gov.coi.cascades.plugin.postgresql;

import com.google.inject.Binder;
import com.google.inject.Module;
import pl.gov.coi.cascades.contract.domain.DatabaseType;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 26.06.18
 */
public class PostgresModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(DatabaseType.class)
            .to(PostgresDatabaseType.class);
    }
}
