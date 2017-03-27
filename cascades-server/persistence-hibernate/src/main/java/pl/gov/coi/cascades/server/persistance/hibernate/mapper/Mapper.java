package pl.gov.coi.cascades.server.persistance.hibernate.mapper;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 24.03.17.
 */
interface Mapper<T, V> {

    T toHibernateEntity(V v);

    V fromHibernateEntity(T t);

}
