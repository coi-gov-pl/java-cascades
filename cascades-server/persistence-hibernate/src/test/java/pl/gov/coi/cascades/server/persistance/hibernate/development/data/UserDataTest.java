package pl.gov.coi.cascades.server.persistance.hibernate.development.data;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.slf4j.Logger;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.database.DatabaseInstanceSupplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.database.Ora12e34Supplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.user.MichaelSupplier;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 04.05.17.
 */
public class UserDataTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Object> typedQuery;

    @Mock
    private Logger logger;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testGetUserForSupplierClass() throws Exception {
        // given
        String username = "michael";
        String email = "michael@example.org";
        Collection<Supplier<User>> supplierList = new ArrayList<>();
        supplierList.add(new MichaelSupplier());
        Collection<DatabaseInstanceSupplier> suppliers = new ArrayList<>();
        UserData userData = new UserData(
            supplierList
        );
        when(entityManager.createQuery(anyString(), any())).thenReturn(typedQuery);
        userData.setEntityManager(entityManager);
        userData.up();
        DatabaseInstanceSupplier databaseSupplier = new Ora12e34Supplier();
        suppliers.add(databaseSupplier);
        Class<? extends Supplier<User>> ownerSupplier = suppliers.iterator().next().getOwnerSupplier();

        // when
        Optional<User> actual = userData.getUserForSupplierClass(
            ownerSupplier
        );

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get().getUsername()).isEqualTo(username);
        assertThat(actual.get().getEmail()).isEqualTo(email);
    }

    @Test
    public void testUp() {
        // given
        Collection<Supplier<User>> supplierList = new ArrayList<>();
        supplierList.add(new MichaelSupplier());
        UserData userData = new UserData(
            supplierList,
            logger
        );
        userData.setEntityManager(entityManager);
        when(logger.isInfoEnabled()).thenReturn(false);

        // when
        userData.up();

        // then
        verify(logger, times(0)).info(anyString(), anyLong());
    }

    @Test
    public void testDown() throws Exception {
        // given
        Collection<Supplier<User>> supplierList = new ArrayList<>();
        supplierList.add(new MichaelSupplier());
        UserData userData = new UserData(
            supplierList
        );
        when(entityManager.createQuery(anyString(), any())).thenReturn(typedQuery);
        userData.setEntityManager(entityManager);
        userData.up();

        // when
        userData.down();

        // then
        verify(entityManager, times(1)).find(any(), anyLong());
    }

}
