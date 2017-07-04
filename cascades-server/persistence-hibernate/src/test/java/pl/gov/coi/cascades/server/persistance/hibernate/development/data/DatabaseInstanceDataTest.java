package pl.gov.coi.cascades.server.persistance.hibernate.development.data;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.slf4j.Logger;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.database.DatabaseInstanceSupplier;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.database.Ora12e34Supplier;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.Template;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 08.05.17.
 */
public class DatabaseInstanceDataTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Object> typedQuery;

    @Mock
    private UserData userData;

    @Mock
    private TemplateIdData templateIdData;

    @Mock
    private Logger logger;

    @Mock
    private User user;

    @Mock
    private Template template;

    @Mock
    private DatabaseInstance databaseInstance;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testUpWhenLoggerIsNotDebugEnabled() {
        // given
        Collection<DatabaseInstanceSupplier> supplierList = new ArrayList<>();
        DatabaseInstanceSupplier databaseSupplier = new Ora12e34Supplier();
        supplierList.add(databaseSupplier);
        DatabaseInstanceData databaseInstanceData = new DatabaseInstanceData(
            logger,
            supplierList,
            userData,
            templateIdData
        );
        databaseInstanceData.setEntityManager(entityManager);
        Class<? extends Supplier<User>> ownerSupplier = supplierList.iterator().next().getOwnerSupplier();
        when(userData.getUserForSupplierClass(ownerSupplier)).thenReturn(Optional.of(user));
        when(logger.isInfoEnabled()).thenReturn(false);
        when(templateIdData.getTemplateIdForSupplierClass(any())).thenReturn(Optional.of(template));

        // when
        databaseInstanceData.up();

        // then
        verify(logger, times(0)).info(anyString(), anyLong());
        verify(userData, times(1)).getUserForSupplierClass(any());
        verify(templateIdData, times(1)).getTemplateIdForSupplierClass(any());
    }

    @Test
    public void testUpWhenLoggerIsDebugEnabled() {
        // given
        Collection<DatabaseInstanceSupplier> supplierList = new ArrayList<>();
        DatabaseInstanceSupplier databaseSupplier = new Ora12e34Supplier();
        supplierList.add(databaseSupplier);
        DatabaseInstanceData databaseInstanceData = new DatabaseInstanceData(
            logger,
            supplierList,
            userData,
            templateIdData
        );
        databaseInstanceData.setEntityManager(entityManager);
        Class<? extends Supplier<User>> ownerSupplier = supplierList.iterator().next().getOwnerSupplier();
        when(userData.getUserForSupplierClass(ownerSupplier)).thenReturn(Optional.of(user));
        when(logger.isInfoEnabled()).thenReturn(true);
        when(templateIdData.getTemplateIdForSupplierClass(any())).thenReturn(Optional.of(template));
        when(entityManager.createQuery(anyString(), any())).thenReturn(typedQuery);

        // when
        databaseInstanceData.up();

        // then
        verify(logger, times(1)).info(contains("20170419:000515"));
        verify(logger, times(1)).info(contains("20170419:000641"));
        verify(userData, times(1)).getUserForSupplierClass(any());
        verify(templateIdData, times(1)).getTemplateIdForSupplierClass(any());
    }

    @Test
    public void testDown() throws Exception {
        // given
        Collection<DatabaseInstanceSupplier> supplierList = new ArrayList<>();
        supplierList.add(new Ora12e34Supplier());
        DatabaseInstanceData databaseInstanceData = new DatabaseInstanceData(
            supplierList,
            userData,
            templateIdData
        );
        when(entityManager.createQuery(anyString(), any())).thenReturn(typedQuery);
        databaseInstanceData.setEntityManager(entityManager);
        Class<? extends Supplier<User>> ownerSupplier = supplierList.iterator().next().getOwnerSupplier();
        when(userData.getUserForSupplierClass(ownerSupplier)).thenReturn(Optional.of(user));
        when(templateIdData.getTemplateIdForSupplierClass(any())).thenReturn(Optional.of(template));
        when(entityManager.createQuery(anyString(), any())).thenReturn(typedQuery);
        when(entityManager.find(any(), anyLong())).thenReturn(databaseInstance);
        databaseInstanceData.up();

        // when
        databaseInstanceData.down();

        // then
        verify(entityManager, times(2)).find(any(), anyLong());
    }

}
