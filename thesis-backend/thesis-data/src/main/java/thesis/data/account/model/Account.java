package thesis.data.account.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import thesis.data.position.model.Position;
import thesis.data.role.model.Role;
import thesis.data.task.model.Task;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@ToString
@RequiredArgsConstructor
@SuperBuilder
@Getter
@Entity
@Table(
        name = "account",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "login")
        }
)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String login;

    private String email;

    private String pass;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatusType status;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @ToString.Exclude
    private AccountDetails details;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @ToString.Exclude
    private Position position;

    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinTable(
            name = "account_role",
            joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @OneToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Task> tasks;

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