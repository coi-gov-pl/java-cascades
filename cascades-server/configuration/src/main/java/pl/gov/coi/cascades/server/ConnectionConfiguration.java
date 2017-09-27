package pl.gov.coi.cascades.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 04.08.17
 */
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
class ConnectionConfiguration {
    private String driver;
    private String url;
}
