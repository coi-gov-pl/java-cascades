package pl.gov.coi.cascades.server.persistance.hibernate.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 30.03.17.
 */
@Entity
@Table
@Getter
@Setter
@ToString
public class TemplateId {

    @Nullable
    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable = false)
    private String templateOfId;

}
