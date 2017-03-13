package pl.gov.coi.cascades.server.domain.deletedatabase;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 09.03.17.
 */
public interface DatabaseInstanceUseCase {

    void execute(DatabaseInstanceRequest request, DatabaseInstanceResponse response);

}
