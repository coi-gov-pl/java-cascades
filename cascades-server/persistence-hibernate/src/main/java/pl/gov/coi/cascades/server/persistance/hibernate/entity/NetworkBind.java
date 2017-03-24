package pl.gov.coi.cascades.server.persistance.hibernate.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 23.03.17.
 */
@Setter
@Getter
@Embeddable
@NoArgsConstructor
public class NetworkBind {

    @Column
    @Nullable
    private String host;

    @Column
    @Nullable
    private Integer port;

}
