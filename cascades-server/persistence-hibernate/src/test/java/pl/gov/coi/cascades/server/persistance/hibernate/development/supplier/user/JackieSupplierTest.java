package pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.user;

import org.junit.Test;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.04.17.
 */
public class JackieSupplierTest {

    @Test
    public void testGet() throws Exception {
        // given
        String username = "jackie";
        String email = "jackie@example.org";
        JackieSupplier jackieSupplier = new JackieSupplier();

        // when
        User actual = jackieSupplier.get();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getUsername()).isEqualTo(username);
        assertThat(actual.getEmail()).isEqualTo(email);
    }

}
