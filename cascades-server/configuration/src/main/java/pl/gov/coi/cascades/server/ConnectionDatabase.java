package pl.gov.coi.cascades.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 01.08.17
 */
@AllArgsConstructor
@Getter
class ConnectionDatabase {
    private JdbcTemplate jdbcTemplate;
    private String type;
}
