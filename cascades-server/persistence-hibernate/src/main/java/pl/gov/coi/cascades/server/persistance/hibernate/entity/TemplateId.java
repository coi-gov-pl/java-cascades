package pl.gov.coi.cascades.server.persistance.hibernate.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 30.03.17.
 */
@Setter
@Getter
@ToString
@Table
@Entity
public class TemplateId {

    @GeneratedValue
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private TemplateIdStatus status;

    @Column
    private boolean isDefault;

    @Column
    private String serverId;

}
