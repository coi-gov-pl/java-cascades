package pl.gov.coi.cascades.server.persistance.hibernate.development;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.gov.coi.cascades.server.Environment;
import pl.gov.coi.cascades.server.persistance.hibernate.development.data.DatabaseInstanceData;
import pl.gov.coi.cascades.server.persistance.hibernate.development.data.JpaDevelopmentDataImpl;
import pl.gov.coi.cascades.server.persistance.hibernate.development.data.UserData;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.database.DatabaseInstanceSupplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.user.JackieSupplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.user.JohnRamboSupplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.user.MichaelSupplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.user.MikolajRoznerskiSupplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.database.Ora12e34Supplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.database.Ora23r45Supplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.database.Pos45y67Supplier;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import javax.inject.Singleton;
import javax.transaction.Transactional;
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
    @Singleton
    @Transactional
    JpaDevelopmentDataImpl provideDevelopmentData(UserData userData,
                                                  DatabaseInstanceData databaseInstanceData) {
        return new JpaDevelopmentDataImpl(
            userData,
            databaseInstanceData
        );
    }

    @Bean
    @DevelopmentBean
    Supplier<User> createJohnRamboProvider() {
        return new JohnRamboSupplier();
    }

    @Bean
    @DevelopmentBean
    Supplier<User> createMikolajRoznerskiProvider() {
        return new MikolajRoznerskiSupplier();
    }

    @Bean
    @DevelopmentBean
    Supplier<User> createJackieProvider() {
        return new JackieSupplier();
    }

    @Bean
    @DevelopmentBean
    Supplier<User> createMichaelProvider() {
        return new MichaelSupplier();
    }

    @Bean
    @Transactional
    @Singleton
    UserData createUserData(List<Supplier<User>> supplierList) {
        Iterable<Supplier<User>> devBeans = supplierList.stream()
            .filter(DevelopmentDataConfiguration::isDevelopmentBean)
            .collect(Collectors.toList());
        return new UserData(devBeans);
    }

    @Bean
    @DevelopmentBean
    DatabaseInstanceSupplier createOra12e34Provider() {
        return new Ora12e34Supplier();
    }

    @Bean
    @DevelopmentBean
    DatabaseInstanceSupplier createPos45y67Provider() {
        return new Pos45y67Supplier();
    }

    @Bean
    @DevelopmentBean
    DatabaseInstanceSupplier createOra23y45Provider() {
        return new Ora23r45Supplier();
    }

    @Bean
    @Transactional
    @Singleton
    DatabaseInstanceData createDatabaseInstanceData(UserData userData,
                                                    List<DatabaseInstanceSupplier> supplierList) {
        Iterable<DatabaseInstanceSupplier> devBeans = supplierList.stream()
            .filter(DevelopmentDataConfiguration::isDevelopmentBean)
            .collect(Collectors.toList());
        return new DatabaseInstanceData(
            devBeans,
            userData
        );
    }

    private static boolean isDevelopmentBean(Supplier<?> supplier) {
        return supplier.getClass().isAnnotationPresent(DevelopmentBean.class);
    }
}
