package pl.thesis.data.account.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.thesis.data.position.model.Position;
import pl.thesis.data.project.model.AccountProject;
import pl.thesis.data.task.model.Task;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@ToString
@RequiredArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Table(
        name = "account",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        }
)
public class  Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String email;

    private String pass;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatusType status;

    @OneToOne(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    private AccountDetails details;

    @ManyToOne
    @JoinColumn(name="position_id")
    @ToString.Exclude
    private Position position;

/*    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @ToString.Exclude
    @JoinTable(
            name = "account_role",
            joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))*/
    @OneToMany(mappedBy = "account")
    private List<AccountRole> accountRoles;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Task> tasks;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<AccountProject> accountProjects;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Account account = (Account) o;
        return id != null && Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}