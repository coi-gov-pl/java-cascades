package pl.gov.coi.cascades.server.domain.deletedatabase;

import pl.gov.coi.cascades.contract.service.Violation;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 09.03.17.
 */
public interface Response {

    boolean isSuccessful();

    void addViolation(Violation violation);

    Iterable<Violation> getViolations();

}
