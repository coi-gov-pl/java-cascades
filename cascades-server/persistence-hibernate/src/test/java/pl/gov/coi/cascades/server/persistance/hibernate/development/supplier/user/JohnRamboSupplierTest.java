package pl.gov.coi.cascades.server.persistance.hibernate.development.supplier.user;

import org.junit.Test;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 26.04.17.
 */
public class JohnRamboSupplierTest {

    @Test
    public void testGet() throws Exception {
        // given
        String username = "jrambo";
        String email = "jrambo@example.org";
        JohnRamboSupplier johnRamboSupplier = new JohnRamboSupplier();

        // when
        User actual = johnRamboSupplier.get();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getUsername()).isEqualTo(username);
        assertThat(actual.getEmail()).isEqualTo(email);
    }

}
