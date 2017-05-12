package pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.user;

import org.junit.Test;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.04.17.
 */
public class MikolajRoznerskiSupplierTest {

    @Test
    public void testGet() throws Exception {
        // given
        String username = "Mikołaj Roznerski";
        String email = "mikolaj.rozneski@example.com";
        MikolajRoznerskiSupplier mikolajRoznerskiSupplier = new MikolajRoznerskiSupplier();

        // when
        User actual = mikolajRoznerskiSupplier.get();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getUsername()).contains(username);
        assertThat(actual.getEmail()).isEqualTo(email);
    }

}
