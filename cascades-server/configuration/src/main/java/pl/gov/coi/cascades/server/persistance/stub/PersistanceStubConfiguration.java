package pl.gov.coi.cascades.server.persistance.stub;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.server.ProfileType;
import pl.gov.coi.cascades.server.domain.*;

import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 05.04.17.
 */
@Profile(ProfileType.STUB_NAME)
@Configuration
class PersistanceStubConfiguration {

    private static final String STUB_DATABASE = "stub-database";

    @Bean
    @Named(STUB_DATABASE)
    Map<Object, User> produceStubDatabase() {
        return new HashMap<>();
    }

    @Bean
    DatabaseTemplateGateway produceDatabaseTemplateGateway() {
        return new DatabaseTemplateGatewayStub();
    }

    @Bean
    TemplateIdGateway produceTemplateIdGateway() {
        return new TemplateIdGatewayStub();
    }

    @Bean
    DatabaseInstanceGateway produceDatabaseInstanceGateway() {
        return new DatabaseInstanceGatewayStub();
    }

    @Bean
    UserGateway produceUserGateway(@Named(STUB_DATABASE) Map<Object, User> database) {
        return new UserGatewayStub(database);
    }

    @Bean
    DatabaseLimitGateway produceDatabaseLimitGateway() {
        return new DatabaseLimitGatewayStub();
    }

    @Bean
    DatabaseIdGateway produceDatabaseIdGateway() {
        return new DatabaseIdGatewayStub();
    }

    @Bean
    DatabaseType produceDatabaseType() {
        return new DatabaseTypeStub();
    }

}
