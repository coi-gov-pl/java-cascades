package pl.gov.coi.cascades.server.persistance.hibernate.development;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.server.persistance.hibernate.development.data.DatabaseInstanceData;
import pl.gov.coi.cascades.server.persistance.hibernate.development.data.JpaDevelopmentDataImpl;
import pl.gov.coi.cascades.server.persistance.hibernate.development.data.TemplateIdData;
import pl.gov.coi.cascades.server.persistance.hibernate.development.data.UserData;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.database.DatabaseInstanceSupplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.database.Ora12e34Supplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.database.Ora23r45Supplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.database.Pos34t56Supplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.database.Pos45y67Supplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.template.Eaba275Supplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.template.F4ab6a58Supplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.user.JackieSupplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.user.JohnRamboSupplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.user.MichaelSupplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.user.MikolajRoznerskiSupplier;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateId;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.04.17.
 */
public class DevelopmentDataConfigurationTest {

    @Mock
    private UserData userData;

    @Mock
    private DatabaseInstanceData databaseInstanceData;

    @Mock
    private TemplateIdData templateIdData;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testProvideDevelopmentData() throws Exception {
        // given
        DevelopmentDataConfiguration developmentDataConfiguration = new DevelopmentDataConfiguration();

        // when
        JpaDevelopmentDataImpl actual = developmentDataConfiguration.provideDevelopmentData(
            userData,
            databaseInstanceData,
            templateIdData
        );

        // then
        assertThat(actual).isInstanceOf(JpaDevelopmentDataImpl.class);
    }

    @Test
    public void testCreateEaba275Provider() throws Exception {
        // given
        DevelopmentDataConfiguration developmentDataConfiguration = new DevelopmentDataConfiguration();

        // when
        Supplier<TemplateId> actual = developmentDataConfiguration.createEaba275Provider();

        // then
        assertThat(actual).isInstanceOf(Eaba275Supplier.class);
    }

    @Test
    public void testCreateF4ab6a58Provider() throws Exception {
        // given
        DevelopmentDataConfiguration developmentDataConfiguration = new DevelopmentDataConfiguration();

        // when
        Supplier<TemplateId> actual = developmentDataConfiguration.createF4ab6a58Provider();

        // then
        assertThat(actual).isInstanceOf(F4ab6a58Supplier.class);
    }

    @Test
    public void testCreateTemplateIdData() throws Exception {
        // given
        List<Supplier<TemplateId>> suppliers = new ArrayList<>();
        suppliers.add(new F4ab6a58Supplier());
        DevelopmentDataConfiguration developmentDataConfiguration = new DevelopmentDataConfiguration();

        // when
        TemplateIdData actual = developmentDataConfiguration.createTemplateIdData(
            suppliers
        );

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testCreateJohnRamboProvider() throws Exception {
        // given
        DevelopmentDataConfiguration developmentDataConfiguration = new DevelopmentDataConfiguration();

        // when
        Supplier<User> actual = developmentDataConfiguration.createJohnRamboProvider();

        // then
        assertThat(actual).isInstanceOf(JohnRamboSupplier.class);
    }

    @Test
    public void testCreateMikolajRoznerskiProvider() throws Exception {
        // given
        DevelopmentDataConfiguration developmentDataConfiguration = new DevelopmentDataConfiguration();

        // when
        Supplier<User> actual = developmentDataConfiguration.createMikolajRoznerskiProvider();

        // then
        assertThat(actual).isInstanceOf(MikolajRoznerskiSupplier.class);
    }

    @Test
    public void testCreateJackieProvider() throws Exception {
        // given
        DevelopmentDataConfiguration developmentDataConfiguration = new DevelopmentDataConfiguration();

        // when
        Supplier<User> actual = developmentDataConfiguration.createJackieProvider();

        // then
        assertThat(actual).isInstanceOf(JackieSupplier.class);
    }

    @Test
    public void testCreateMichaelProvider() throws Exception {
        // given
        DevelopmentDataConfiguration developmentDataConfiguration = new DevelopmentDataConfiguration();

        // when
        Supplier<User> actual = developmentDataConfiguration.createMichaelProvider();

        // then
        assertThat(actual).isInstanceOf(MichaelSupplier.class);
    }

    @Test
    public void testCreateUserData() throws Exception {
        // given
        List<Supplier<User>> suppliers = new ArrayList<>();
        suppliers.add(new MichaelSupplier());
        DevelopmentDataConfiguration developmentDataConfiguration = new DevelopmentDataConfiguration();

        // when
        UserData actual = developmentDataConfiguration.createUserData(
            suppliers
        );

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    public void testCreateOra12e34Provider() throws Exception {
        // given
        DevelopmentDataConfiguration developmentDataConfiguration = new DevelopmentDataConfiguration();

        // when
        DatabaseInstanceSupplier actual = developmentDataConfiguration.createOra12e34Provider();

        // then
        assertThat(actual).isInstanceOf(Ora12e34Supplier.class);
    }

    @Test
    public void testCreatePos45y67Provider() throws Exception {
        // given
        DevelopmentDataConfiguration developmentDataConfiguration = new DevelopmentDataConfiguration();

        // when
        DatabaseInstanceSupplier actual = developmentDataConfiguration.createPos45y67Provider();

        // then
        assertThat(actual).isInstanceOf(Pos45y67Supplier.class);
    }

    @Test
    public void testCreateOra23y45Provider() throws Exception {
        // given
        DevelopmentDataConfiguration developmentDataConfiguration = new DevelopmentDataConfiguration();

        // when
        DatabaseInstanceSupplier actual = developmentDataConfiguration.createOra23y45Provider();

        // then
        assertThat(actual).isInstanceOf(Ora23r45Supplier.class);
    }

    @Test
    public void testCreatePos34t56Provider() throws Exception {
        // given
        DevelopmentDataConfiguration developmentDataConfiguration = new DevelopmentDataConfiguration();

        // when
        DatabaseInstanceSupplier actual = developmentDataConfiguration.createPos34t56Provider();

        // then
        assertThat(actual).isInstanceOf(Pos34t56Supplier.class);
    }

    @Test
    public void testCreateDatabaseInstanceData() throws Exception {
        // given
        List<DatabaseInstanceSupplier> suppliers = new ArrayList<>();
        suppliers.add(new Pos34t56Supplier());
        DevelopmentDataConfiguration developmentDataConfiguration = new DevelopmentDataConfiguration();

        // when
        DatabaseInstanceData actual = developmentDataConfiguration.createDatabaseInstanceData(
            userData,
            suppliers,
            templateIdData
        );

        // then
        assertThat(actual).isNotNull();
    }

}
