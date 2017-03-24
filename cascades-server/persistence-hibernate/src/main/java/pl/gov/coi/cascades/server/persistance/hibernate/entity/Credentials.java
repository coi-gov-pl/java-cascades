package pl.gov.coi.cascades.server.persistance.hibernate.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 23.03.17.
 */
@Setter
@Getter
@Embeddable
public class Credentials {

    @Column
    private String username;

    @Column
    private String password;

}
