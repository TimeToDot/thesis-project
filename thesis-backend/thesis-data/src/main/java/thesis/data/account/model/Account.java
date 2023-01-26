package thesis.data.account.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import thesis.data.position.model.Position;
import thesis.data.project.model.AccountProject;
import thesis.data.role.model.Role;
import thesis.data.task.model.Task;

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

    //private String login;

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

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @ToString.Exclude
    @JoinTable(
            name = "account_role",
            joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

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