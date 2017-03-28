package pl.gov.coi.cascades.server.persistance.hibernate.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 23.03.17.
 */
@Entity
@Table
@Getter
@Setter
@ToString
public class User {

    @Column
    @Nullable
    private String username;

    @GeneratedValue
    @Nullable
    @Id
    private Long id;

    @Column
    @Nullable
    private String email;

    @OneToMany
    @JoinColumn
    private Set<DatabaseInstance> databases = new HashSet<>();

    public void addDatabase(DatabaseInstance instance) {
        databases.add(instance);
    }
}
