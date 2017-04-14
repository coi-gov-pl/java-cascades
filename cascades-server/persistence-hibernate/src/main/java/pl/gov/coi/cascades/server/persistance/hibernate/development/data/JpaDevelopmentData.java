package pl.gov.coi.cascades.server.persistance.hibernate.development.data;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 30.03.17.
 */
public interface JpaDevelopmentData {

    void up();

    void down();

}
