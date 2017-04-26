package pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.user;

import org.junit.Test;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.04.17.
 */
public class MichaelSupplierTest {

    @Test
    public void testGet() throws Exception {
        // given
        String username = "michael";
        String email = "michael@example.org";
        MichaelSupplier michaelSupplier = new MichaelSupplier();

        // when
        User actual = michaelSupplier.get();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getUsername()).isEqualTo(username);
        assertThat(actual.getEmail()).isEqualTo(email);
    }

}
