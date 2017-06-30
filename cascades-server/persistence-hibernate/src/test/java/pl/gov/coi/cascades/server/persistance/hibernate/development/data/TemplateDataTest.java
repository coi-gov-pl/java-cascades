package pl.gov.coi.cascades.server.persistance.hibernate.development.data;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.template.Eaba275Supplier;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.Template;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateIdStatus;

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
public class TemplateDataTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Object> typedQuery;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testGetUserForSupplierClass() throws Exception {
        // given
        String name = "oracle_template";
        String serverId = "rgey65getg";
        Collection<Supplier<Template>> supplierList = new ArrayList<>();
        supplierList.add(new Eaba275Supplier());
        TemplateIdData userData = new TemplateIdData(
            supplierList
        );
        when(entityManager.createQuery(anyString(), any())).thenReturn(typedQuery);
        userData.setEntityManager(entityManager);
        userData.up();

        // when
        Optional<Template> actual = userData.getTemplateIdForSupplierClass(
            Eaba275Supplier.class
        );

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get().getName()).isEqualTo(name);
        assertThat(actual.get().getServerId()).isEqualTo(serverId);
        assertThat(actual.get().isDefault()).isTrue();
        assertThat(actual.get().getStatus()).isEqualTo(TemplateIdStatus.CREATED);
    }

    @Test
    public void testDown() throws Exception {
        // given
        Collection<Supplier<Template>> supplierList = new ArrayList<>();
        supplierList.add(new Eaba275Supplier());
        TemplateIdData userData = new TemplateIdData(
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
