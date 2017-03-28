package pl.gov.coi.cascades.server.persistance.hibernate.development;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.gov.coi.cascades.server.Environment;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.03.17.
 */
@Configuration
@Profile(Environment.DEVELOPMENT_NAME)
class DevelopmentDataConfiguration {

    @Bean
    JpaDevelopmentData provideDevelopmentData(UserData userData,
                                              DatabaseInstanceData databaseInstanceData) {
        return new JpaDevelopmentData(
            userData,
            databaseInstanceData
        );
    }

    @Bean @DevelopmentBean
    Supplier<User> createJohnRamboProvider() {
        return new JohnRamboSupplier();
    }

    @Bean @DevelopmentBean
    Supplier<User> createMikolajRoznerskiProvider() {
        return new MikolajRoznerskiSupplier();
    }

    @Bean
    UserData createUserData(List<Supplier<User>> supplierList) {
        Iterable<Supplier<User>> devBeans = supplierList.stream()
            .filter(this::isDevelopmentBean)
            .collect(Collectors.toList());
        return new UserData(devBeans);
    }

    @Bean @DevelopmentBean
    DatabaseInstanceSupplier createOra12e34Provder() {
        return new Ora12e34Supplier();
    }

    @Bean
    DatabaseInstanceData createDatabaseInstanceData(UserData userData,
                                                    List<DatabaseInstanceSupplier> supplierList) {
        Iterable<DatabaseInstanceSupplier> devBeans = supplierList.stream()
            .filter(this::isDevelopmentBean)
            .collect(Collectors.toList());
        return new DatabaseInstanceData(
            devBeans,
            userData
        );
    }

    private boolean isDevelopmentBean(Supplier<?> supplier) {
        return supplier.getClass().isAnnotationPresent(DevelopmentBean.class);
    }
}
